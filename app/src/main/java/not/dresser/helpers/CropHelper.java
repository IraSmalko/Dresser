package not.dresser.helpers;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import com.android.camera.CropImageIntentBuilder;

import java.io.File;
import java.util.Random;

public class CropHelper {

    public static final int REQUEST_CROP_PICTURE = 2;

    private Context mContext;
    private OnCrop mOnCropListener;
    private Uri mCropImageUri;

    public CropHelper(Context context, OnCrop onCropListener) {
        mContext = context;
        mOnCropListener = onCropListener;
    }

    public String randomPhotoName() {
        final Random random = new Random();
        return String.valueOf(random.nextInt()) + "photoCrop.jpg";
    }

    private Uri createFileUriCrop() {
        File file = new File(mContext.getCacheDir(), randomPhotoName());

        return FileProvider.getUriForFile(mContext, "not.dresser", file);
    }

    public void cropImage(Uri photoUri, int height, int width) {
        mCropImageUri = createFileUriCrop();
        CropImageIntentBuilder cropImage = new CropImageIntentBuilder(width, height, width, height, mCropImageUri);
        cropImage.setOutlineColor(0xFF03A9F4);
        cropImage.setSourceImage(photoUri);
        ActivityCompat.startActivityForResult((AppCompatActivity) mContext, cropImage
                .getIntent(mContext), REQUEST_CROP_PICTURE, null);
    }

    public void onActivityResult(int resultCode, int requestCode) {
        switch (requestCode) {
            case REQUEST_CROP_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    mOnCropListener.onCrop(mCropImageUri);
                }
                break;
        }
    }

    public interface OnCrop {
        void onCrop(Uri cropImageUri);
    }
}
