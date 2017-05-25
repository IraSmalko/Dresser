package not.dresser;


import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class CRUDRealm {

    private Realm mRealm;

    public CRUDRealm() {
        mRealm = Realm.getDefaultInstance();
    }

    public List<ClothingItem> allObjects (){
       return mRealm.where(ClothingItem.class).findAll();
    }

    public void addClothingItem(String name, String photoUrl, String category, String occasion, String season) {
        mRealm.beginTransaction();
        ClothingItem clothingItem = mRealm.createObject(ClothingItem.class);
        clothingItem.setName(name);
        clothingItem.setCategory(category);
        clothingItem.setOccasion(occasion);
        clothingItem.setPhotoUrl(photoUrl);
        clothingItem.setSeason(season);
        mRealm.commitTransaction();
    }

    public void removeClothingItem(int id) {
        mRealm.beginTransaction();
        RealmResults<ClothingItem> clothingItems = mRealm.where(ClothingItem.class).equalTo("id", id).findAll();

        if (!clothingItems.isEmpty()) {
            for (int i = clothingItems.size() - 1; i >= 0; i--) {
                clothingItems.get(i).deleteFromRealm();
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
