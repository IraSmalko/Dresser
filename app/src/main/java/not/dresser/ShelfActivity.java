package not.dresser;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import static not.dresser.CropHelper.REQUEST_CROP_PICTURE;
import static not.dresser.MainActivity.NAME;
import static not.dresser.PhotoFromCameraHelper.GALLERY_REQUEST;
import static not.dresser.PhotoFromCameraHelper.REQUEST_IMAGE_CAPTURE;

public class ShelfActivity extends AppCompatActivity {

    private PhotoFromCameraHelper mPhotoFromCameraHelper;
    private CropHelper mCropHelper;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelf_activity);
        ActionBar actionBar = getSupportActionBar();
        mImageView = (ImageView) findViewById(R.id.photoImageView);
        ImageButton btnPhotoFromGallery = (ImageButton) findViewById(R.id.categoryPhotoUrlGallery);
        ImageButton btnPhotoFromCamera = (ImageButton) findViewById(R.id.categoryPhotoUrlCamera);
        Button btnSave = (Button) findViewById(R.id.btnSave);

        Intent intent = getIntent();
        assert actionBar != null;
        actionBar.setTitle(intent.getStringExtra(NAME));

        if (!intent.getStringExtra(NAME).contains("-")) {
            int photoId = getResources().getIdentifier(intent.getStringExtra(NAME), "drawable",
                    getApplicationContext().getPackageName());
            mImageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), photoId));
        }

        mCropHelper = new CropHelper(this, new CropHelper.OnCrop() {
            @Override
            public void onCrop(Uri cropImageUri) {
                Glide.with(getApplicationContext()).load(cropImageUri).into(mImageView);
            }
        });

        mPhotoFromCameraHelper = new PhotoFromCameraHelper(this, new PhotoFromCameraHelper.OnPhotoPicked() {
            @Override
            public void onPicked(Uri photoUri) {
                mCropHelper.cropImage(photoUri);
            }
        });

        btnSave.setOnClickListener(onClickListener);
        btnPhotoFromCamera.setOnClickListener(onClickListener);
        btnPhotoFromGallery.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.categoryPhotoUrlGallery:
                    mPhotoFromCameraHelper.pickPhoto();
                    break;
                case R.id.categoryPhotoUrlCamera:
                    mPhotoFromCameraHelper.takePhoto();
                    break;
                case R.id.btnSave:

                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == REQUEST_IMAGE_CAPTURE || requestCode == GALLERY_REQUEST) {
            mPhotoFromCameraHelper.onActivityResult(resultCode, requestCode, imageReturnedIntent);
        } else if (requestCode == REQUEST_CROP_PICTURE) {
            if (resultCode == RESULT_OK) {
                mCropHelper.onActivityResult(resultCode, requestCode);
            }
        }
    }
}
