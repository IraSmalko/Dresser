package not.dresser.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.jcmore2.collage.CollageView;

import not.dresser.R;
import not.dresser.helpers.CRUDImage;
import not.dresser.helpers.CRUDRealm;
import not.dresser.helpers.CropHelper;
import not.dresser.helpers.PhotoLoaderAsyncTask;

import static not.dresser.activities.ShelfActivity.IMAGE_URL;
import static not.dresser.helpers.CropHelper.REQUEST_CROP_PICTURE;

public class CollageActivity extends AppCompatActivity {

    private FloatingActionButton mFab;
    private CropHelper mCropHelper;
    private String mScreenshotUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collage_activity);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;

        CollageView collage = (CollageView) findViewById(R.id.collage);
        collage.getLayoutParams().height = (int) (screenHeight * 0.7);
        Intent intent = getIntent();

        for (String url : intent.getStringArrayExtra(IMAGE_URL)) {
            if (url != null) {
                collage.addCard(new CRUDImage().getBitmap(getContentResolver(), url));
            }
        }

        mCropHelper = new CropHelper(this, new CropHelper.OnCrop() {
            @Override
            public void onCrop(Uri cropImageUri) {
                new PhotoLoaderAsyncTask(getApplicationContext(), new PhotoLoaderAsyncTask.PhotoLoadProcessed() {
                    @Override
                    public void onBitmapReady(Bitmap bitmap) {
                        new CRUDImage().delete(getContentResolver(), mScreenshotUrl);
                        new CRUDRealm().addClothingLook(new CRUDImage().add(getContentResolver(), bitmap));
                        startActivity(new Intent(getApplicationContext(), LookActivity.class));
                    }
                }).execute(cropImageUri);
            }
        });

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScreenshotUrl = new CRUDImage().takeScreenshot(getContentResolver(), mFab, getWindow());
                mCropHelper.cropImage(Uri.parse(mScreenshotUrl), 1048, 780);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == REQUEST_CROP_PICTURE) {
            if (resultCode == RESULT_OK) {
                mCropHelper.onActivityResult(resultCode, requestCode);
            }
        }
    }
}
