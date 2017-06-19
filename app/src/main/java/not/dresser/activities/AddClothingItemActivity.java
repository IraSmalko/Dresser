package not.dresser.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import not.dresser.R;
import not.dresser.entity.ClothingItem;
import not.dresser.helpers.CRUDRealm;
import not.dresser.helpers.CropHelper;
import not.dresser.helpers.PermissionsHelper;
import not.dresser.helpers.PhotoFromCameraHelper;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static not.dresser.activities.MainActivity.NAME;
import static not.dresser.helpers.CropHelper.REQUEST_CROP_PICTURE;
import static not.dresser.helpers.PermissionsHelper.CAMERA_PERMISSION_REQUEST;
import static not.dresser.helpers.PermissionsHelper.WRITE_EXTERNAL_STORAGE_REQUEST;
import static not.dresser.helpers.PhotoFromCameraHelper.GALLERY_REQUEST;
import static not.dresser.helpers.PhotoFromCameraHelper.REQUEST_IMAGE_CAPTURE;

public class AddClothingItemActivity extends AppCompatActivity {

    private PhotoFromCameraHelper mPhotoFromCameraHelper;
    private CropHelper mCropHelper;
    private ImageView mImageView;
    private Spinner mCategorySpinner, mOccasionSpinner, mSeasonSpinner;
    private EditText mInputName;
    private String mPhotoUrl;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_clothing_item_activity);

        ActionBar actionBar = getSupportActionBar();
        mInputName = (EditText) findViewById(R.id.name);
        mImageView = (ImageView) findViewById(R.id.photoImageView);
        ImageButton btnPhotoFromGallery = (ImageButton) findViewById(R.id.categoryPhotoUrlGallery);
        ImageButton btnPhotoFromCamera = (ImageButton) findViewById(R.id.categoryPhotoUrlCamera);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        mCategorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        mOccasionSpinner = (Spinner) findViewById(R.id.occasionSpinner);
        mSeasonSpinner = (Spinner) findViewById(R.id.seasonSpinner);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.category));
        ArrayAdapter<String> occasionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.occasion));
        ArrayAdapter<String> seasonAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.season));
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        occasionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategorySpinner.setAdapter(categoryAdapter);
        mOccasionSpinner.setAdapter(occasionAdapter);
        mSeasonSpinner.setAdapter(seasonAdapter);
        mCategorySpinner.setPrompt("Category");
        mOccasionSpinner.setPrompt("Occasion");
        mSeasonSpinner.setPrompt("Season");

        mIntent = getIntent();
        assert actionBar != null;
        actionBar.setTitle(mIntent.getStringExtra(NAME));

        int photoId;
        if (!mIntent.getStringExtra(NAME).contains("-")) {
            photoId = getResources().getIdentifier(mIntent.getStringExtra(NAME), "drawable",
                    getApplicationContext().getPackageName());
        } else {
            photoId = getResources().getIdentifier(mIntent.getStringExtra(NAME).replace("-", "_").toLowerCase(),
                    "drawable", getApplicationContext().getPackageName());
        }
        mImageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), photoId));

        mCropHelper = new CropHelper(this, new CropHelper.OnCrop() {
            @Override
            public void onCrop(Uri cropImageUri) {
                Glide.with(getApplicationContext())
                        .load(cropImageUri)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>(1048, 1048) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                mImageView.setImageBitmap(resource);
                                mPhotoUrl = MediaStore.Images.Media.insertImage(getContentResolver(),
                                        resource, null, null);
                            }
                        });
            }
        });

        mPhotoFromCameraHelper = new PhotoFromCameraHelper(this, new PhotoFromCameraHelper.OnPhotoPicked() {
            @Override
            public void onPicked(Uri photoUri) {
                mCropHelper.cropImage(photoUri, 1048, 1048);
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
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), CAMERA) != PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(AddClothingItemActivity.this, CAMERA)) {
                            new PermissionsHelper().showPermissionDialog(CAMERA_PERMISSION_REQUEST);
                        } else {
                            ActivityCompat.requestPermissions(AddClothingItemActivity.this,
                                    new String[]{CAMERA}, CAMERA_PERMISSION_REQUEST);
                        }
                    } else {
                        mPhotoFromCameraHelper.takePhoto();
                    }
                    break;
                case R.id.btnSave:
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE)
                            != PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(AddClothingItemActivity.this,
                                WRITE_EXTERNAL_STORAGE)) {
                            new PermissionsHelper().showPermissionDialog(WRITE_EXTERNAL_STORAGE_REQUEST);
                        } else {
                            ActivityCompat.requestPermissions(AddClothingItemActivity.this,
                                    new String[]{WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST);
                        }
                    } else {
                        saveButtonPressed();
                    }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults[0] == PERMISSION_GRANTED) {
                mPhotoFromCameraHelper.takePhoto();
            } else {
                new PermissionsHelper(AddClothingItemActivity.this)
                        .showPermissionDialog(CAMERA_PERMISSION_REQUEST);
            }
        } else if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST) {
            if (grantResults[0] == PERMISSION_GRANTED) {
                saveButtonPressed();
            } else {
                new PermissionsHelper(AddClothingItemActivity.this)
                        .showPermissionDialog(WRITE_EXTERNAL_STORAGE_REQUEST);
            }
        }
    }

    private void saveButtonPressed() {
        if (mPhotoUrl != null && !mInputName.getText().toString().equals("")) {
            int exist = 0;
            for (ClothingItem clothingItem : new CRUDRealm().allObjects()) {
                if (clothingItem.getPhotoUrl().equals(mPhotoUrl)) {
                    exist = 1;
                }
            }
            if (exist != 1) {
                String itemCategorySpinner = mCategorySpinner.getSelectedItem().toString();
                String itemOccasionSpinnerSpinner = mOccasionSpinner.getSelectedItem().toString();
                String itemSeasonSpinner = mSeasonSpinner.getSelectedItem().toString();
                int id = new CRUDRealm().addClothingItem(mInputName.getText().toString(), mPhotoUrl,
                        itemCategorySpinner, itemOccasionSpinnerSpinner, itemSeasonSpinner);
                Toast.makeText(getApplicationContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
            }else Toast.makeText(getApplicationContext(), getResources().getString(R.string.photo_exists),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), ShelfListActivity.class);
        intent.putExtra(NAME, mIntent.getStringExtra(NAME));
        startActivity(intent);
    }
}
