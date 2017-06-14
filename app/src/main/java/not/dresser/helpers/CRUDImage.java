package not.dresser.helpers;


import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Window;

import java.io.IOException;

public class CRUDImage {

    public String add(ContentResolver resolver, Bitmap bitmap){
        return MediaStore.Images.Media.insertImage(resolver, bitmap, null, null);
    }

    public int delete(ContentResolver resolver, String photoUrl){
        return resolver.delete(Uri.parse(photoUrl), null, null);
    }

    public String takeScreenshot(ContentResolver resolver, FloatingActionButton fab, Window window) {
        fab.setVisibility(View.INVISIBLE);
        View v1 = window.getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
        return  add(resolver, bitmap);
    }

    public Bitmap getBitmap(ContentResolver resolver, String url){
        Bitmap bitmap = null;
        try {
            bitmap =  MediaStore.Images.Media.getBitmap(resolver, Uri.parse(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
