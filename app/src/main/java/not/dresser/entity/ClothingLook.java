package not.dresser.entity;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ClothingLook extends RealmObject {

    @PrimaryKey
    private int id;
    private String photoUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
