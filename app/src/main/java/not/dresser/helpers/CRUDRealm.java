package not.dresser.helpers;


import android.content.ContentResolver;
import android.net.Uri;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import not.dresser.entity.ClothingItem;

public class CRUDRealm {

    private Realm mRealm;

    public CRUDRealm() {
        mRealm = Realm.getDefaultInstance();
    }

    private List<ClothingItem> allObjects() {
        return mRealm.where(ClothingItem.class).findAll();
    }

    public int addClothingItem(String name, String photoUrl, String category, String occasion, String season) {
        mRealm.beginTransaction();
        int id = allObjects().size() + 1;
        ClothingItem clothingItem = new ClothingItem();
        clothingItem.setId(id);
        clothingItem.setName(name);
        clothingItem.setCategory(category);
        clothingItem.setOccasion(occasion);
        clothingItem.setPhotoUrl(photoUrl);
        clothingItem.setSeason(season);
        mRealm.copyToRealmOrUpdate(clothingItem);
        mRealm.commitTransaction();
        return clothingItem.getId();
    }

    public List<ClothingItem> getClothingList(String categoryName) {
        return mRealm.where(ClothingItem.class).equalTo("category", categoryName).findAll();
    }

    public void removeClothingItem(int id, ContentResolver resolver) {
        mRealm.beginTransaction();
        RealmResults<ClothingItem> clothingItems = mRealm.where(ClothingItem.class).equalTo("id", id).findAll();

        if (!clothingItems.isEmpty()) {
            for (int i = clothingItems.size() - 1; i >= 0; i--) {
                int deletedImg = resolver.delete(Uri.parse(clothingItems.get(i).getPhotoUrl()), null, null);
                if (deletedImg == 1) {
                    clothingItems.get(i).deleteFromRealm();
                }
            }
        }
        mRealm.commitTransaction();
    }

    public static void deleteAllClothing() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<ClothingItem> results = realm.where(ClothingItem.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }
}
