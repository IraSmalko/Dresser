package not.dresser.activities;


import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import not.dresser.R;

import static not.dresser.activities.ShelfListActivity.CATEGORY;
import static not.dresser.activities.ShelfListActivity.PHOTO_URL;

public class ConstructorActivity extends AppCompatActivity {

    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constructor_activity);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;
        int imageViewSize = screenWidth * 3 / 4;

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface_view);

        Intent intent = getIntent();

        switch (intent.getStringExtra(CATEGORY)) {
            case "pants":
                imageView.setY(screenHeight * 1 / 3);
                break;
            case "skirt":
            case "shorts":
                imageView.setY(screenHeight * 1/3);
                imageView.getLayoutParams().width = screenWidth * 2 / 4;
                imageView.getLayoutParams().height = screenWidth * 2 / 4;
                break;
            case "dress":
                imageView.getLayoutParams().width = screenWidth;
                imageView.getLayoutParams().height = screenWidth;
                break;
            default:
                imageView.getLayoutParams().width = imageViewSize;
                imageView.getLayoutParams().height = imageViewSize;
        }

        Glide.with(this).load(intent.getStringExtra(PHOTO_URL)).into(imageView);

        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    camera.setPreviewDisplay(holder);
                    camera.setDisplayOrientation(90);
                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null)
            camera.release();
        camera = null;
    }
}
