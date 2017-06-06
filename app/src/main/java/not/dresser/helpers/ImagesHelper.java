package not.dresser.helpers;


import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import static android.content.ContentValues.TAG;

public class ImagesHelper {

    public static File getPathForNewPhoto(String name, Context context) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("Images", Context.MODE_PRIVATE);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File filePath = new File(directory, name );
        return filePath;
    }

    public static void deleteImage(ContentResolver resolver, Uri uri) {
        try {
            resolver.delete(uri, null, null);
        } catch (Throwable th) {
            Log.e(TAG, "Failed to delete image: " + uri);
        }
    }
}
