package not.dresser.helpers;


import not.dresser.entity.ClothingItem;

public class SaveClothingItem {

    public SaveClothingItem(){
    }

    public int saveButtonPressed(String photoUrl, String inputName, String categorySpinner,
                                  String occasionSpinner, String seasonSpinner) {
        if (photoUrl != null && !inputName.equals("")) {
            int exist = 0;
            for (ClothingItem clothingItem : new CRUDRealm().allObjects()) {
                if (clothingItem.getPhotoUrl().equals(photoUrl)) {
                    exist = 1;
                }
            }
            if (exist != 1) {
               return new CRUDRealm().addClothingItem(inputName, photoUrl, categorySpinner,
                        occasionSpinner, seasonSpinner);
            } else
                return 0;
        }
        return -1;
    }
}
