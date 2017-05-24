package not.dresser;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import java.io.File;

public class CropHelper {

    static final int REQUEST_CROP_PICTURE = 2;
    private Context mContext;
    private OnCrop mOnCropListener;
    private Uri mCropImageUri;

    public CropHelper(Context context, OnCrop onCropListener) {
        mContext = context;
        mOnCropListener = onCropListener;
    }

    private Uri createFileUriCrop() {
        File file = new File(mContext.getCacheDir(), "photoCrop.jpg");

        return FileProvider.getUriForFile(mContext, "not.dresser", file);
    }

    public void cropImage(Uri photoUri) {
        mCropImageUri = createFileUriCrop();
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(photoUri, "image/*");
        cropIntent.putExtra("crop", true);
        cropIntent.putExtra("aspectX", 33);
        cropIntent.putExtra("aspectY", 24);
        cropIntent.putExtra("outputX", 660);
        cropIntent.putExtra("outputY", 480);
        cropIntent.putExtra("return-data", true);
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCropImageUri);
        ActivityCompat.startActivityForResult((AppCompatActivity)mContext, cropIntent,
                REQUEST_CROP_PICTURE, null);
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
