package not.dresser.helpers;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.concurrent.ExecutionException;

public class PhotoLoaderAsyncTask extends AsyncTask<Uri, Bitmap, Bitmap> {

    private Context mContext;
    private PhotoLoadProcessed mPhotoLoadProcessed;

    public PhotoLoaderAsyncTask(Context context, PhotoLoadProcessed photoLoadProcessed) {
        mContext = context;
        mPhotoLoadProcessed = photoLoadProcessed;
    }

    @Override
    protected Bitmap doInBackground(Uri... uris) {
        Uri photoUri = uris[0];
        Bitmap theBitmap = null;
        try {
            theBitmap = Glide
                    .with(mContext)
                    .load(photoUri)
                    .asBitmap()
                    .into(660, 480)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return theBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mPhotoLoadProcessed.onBitmapReady(bitmap);
    }

    public interface PhotoLoadProcessed {
        void onBitmapReady(Bitmap bitmap);
    }
}
