package not.dresser;


import android.graphics.drawable.Drawable;


public class Category {

    private Drawable photo;
    private String name;

    public Category(){}

    public Category(Drawable photo, String name){
        this.name = name;
        this.photo = photo;
    }

    public Drawable getPhoto() {
        return photo;
    }

    public void setPhoto(Drawable photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
