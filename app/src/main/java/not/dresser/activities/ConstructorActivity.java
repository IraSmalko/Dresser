package not.dresser.activities;


import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import not.dresser.R;
import not.dresser.helpers.ZoomInZoomOutHelper;

import static not.dresser.activities.ShelfListActivity.PHOTO_URL;

public class ConstructorActivity extends AppCompatActivity implements View.OnTouchListener {

    private Camera mCamera;

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;

    // These matrices will be used to scale points of the image
    private Matrix mMatrix = new Matrix();
    private Matrix mSavedMatrix = new Matrix();

    // these PointF objects are used to record the point(s) the user is touching
    private PointF mStart = new PointF();
    private PointF mMid = new PointF();
    private float mOldDist = 1f;
    private int mMode = NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constructor_activity);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface_view);

        Intent intent = getIntent();
        Glide.with(this).load(intent.getStringExtra(PHOTO_URL)).into(imageView);

        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    mCamera.setPreviewDisplay(holder);
                    mCamera.setDisplayOrientation(90);
                    mCamera.startPreview();
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

        imageView.setOnTouchListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera = Camera.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null)
            mCamera.release();
        mCamera = null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;
        ZoomInZoomOutHelper zoomHelper = new ZoomInZoomOutHelper();
        zoomHelper.dumpEvent(event);
        // Handle touch events here...

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                mSavedMatrix.set(mMatrix);
                mStart.set(event.getX(), event.getY());
                mMode = DRAG;
                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                mMode = NONE;
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                mOldDist = zoomHelper.spacing(event);
                if (mOldDist > 5f) {
                    mSavedMatrix.set(mMatrix);
                    zoomHelper.midPoint(mMid, event);
                    mMode = ZOOM;
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mMode == DRAG) {
                    mMatrix.set(mSavedMatrix);
                    mMatrix.postTranslate(event.getX() - mStart.x, event.getY() - mStart.y); // create the transformation in the mMatrix  of points
                } else if (mMode == ZOOM) {
                    // pinch zooming
                    float newDist = zoomHelper.spacing(event);
                    if (newDist > 5f) {
                        mMatrix.set(mSavedMatrix);
                        scale = newDist / mOldDist; // setting the scaling of the
                        // mMatrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        mMatrix.postScale(scale, scale, mMid.x, mMid.y);
                    }
                }
                break;
        }
        view.setImageMatrix(mMatrix); // display the transformation on screen

        return true; // indicate event was handled
    }
}
