package not.dresser.helpers;


import android.content.ContentResolver;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import not.dresser.entity.ClothingItem;
import not.dresser.entity.ClothingLook;

public class CRUDRealm {

    private Realm mRealm;

    public CRUDRealm() {
        mRealm = Realm.getDefaultInstance();
    }

    public List<ClothingItem> allObjects() {
        return mRealm.where(ClothingItem.class).findAll();
    }

    private int createClothingItemId(){
        int id = 0;
        lab1:for (ClothingItem ci : allObjects()) {
            for (int i = 0; i <= allObjects().size(); i++) {
                if (ci.getId() != i) {
                    id = i;
                    break lab1;
                }
            }
        }
        return id;
    }

    private int createClothingLookId(){
        int id = 0;
        lab1:for (ClothingLook ci : getClothingLooks()) {
            for (int i = 0; i <= allObjects().size(); i++) {
                if (ci.getId() != i) {
                    id = i;
                    break lab1;
                }
            }
        }
        return id;
    }

    public int addClothingItem(String name, String photoUrl, String category, String occasion, String season) {
        mRealm.beginTransaction();
        ClothingItem clothingItem = mRealm.createObject(ClothingItem.class, createClothingItemId());
        clothingItem.setName(name);
        clothingItem.setCategory(category);
        clothingItem.setOccasion(occasion);
        clothingItem.setPhotoUrl(photoUrl);
        clothingItem.setSeason(season);
        mRealm.commitTransaction();
        return clothingItem.getId();
    }

    public List<ClothingLook> getClothingLooks() {
        return mRealm.where(ClothingLook.class).findAll();
    }

    public int addClothingLook(String photoUrl) {
        mRealm.beginTransaction();
        ClothingLook clothingLook = mRealm.createObject(ClothingLook.class, createClothingLookId());
        clothingLook.setPhotoUrl(photoUrl);
        mRealm.commitTransaction();
        return clothingLook.getId();
    }

    public List<ClothingItem> getClothingList(String categoryName) {
        return mRealm.where(ClothingItem.class).equalTo("category", categoryName).findAll();
    }

    public void removeClothingItem(int id, ContentResolver resolver) {
        mRealm.beginTransaction();
        RealmResults<ClothingItem> clothingItems = mRealm.where(ClothingItem.class).equalTo("id", id).findAll();

        if (!clothingItems.isEmpty()) {
            for (int i = clothingItems.size() - 1; i >= 0; i--) {
                int deletedImg = new CRUDImage().delete(resolver, clothingItems.get(i).getPhotoUrl());
                if (deletedImg == 1) {
                    clothingItems.get(i).deleteFromRealm();
                }
            }
        }
        mRealm.commitTransaction();
    }

    public void removeClothingLook(int id, ContentResolver resolver) {
        mRealm.beginTransaction();
        RealmResults<ClothingLook> clothingLooks = mRealm.where(ClothingLook.class).equalTo("id", id).findAll();

        if (!clothingLooks.isEmpty()) {
            for (int i = clothingLooks.size() - 1; i >= 0; i--) {
                int deletedImg = new CRUDImage().delete(resolver, clothingLooks.get(i).getPhotoUrl());
                if (deletedImg == 1) {
                    clothingLooks.get(i).deleteFromRealm();
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
