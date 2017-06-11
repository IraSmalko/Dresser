package not.dresser.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import not.dresser.R;
import not.dresser.helpers.PermissionsHelper;

import static android.Manifest.permission.CAMERA;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static not.dresser.activities.MainActivity.NAME;
import static not.dresser.activities.ShelfListActivity.CATEGORY;
import static not.dresser.activities.ShelfListActivity.OCCASION;
import static not.dresser.activities.ShelfListActivity.PHOTO_URL;
import static not.dresser.activities.ShelfListActivity.SEASON;
import static not.dresser.helpers.PermissionsHelper.CAMERA_PERMISSION_REQUEST;

public class ClothingItemActivity extends AppCompatActivity {

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clothing_item_activity);

        ImageView imageView = (ImageView) findViewById(R.id.photoImageView);
        TextView category = (TextView) findViewById(R.id.category);
        TextView occasion = (TextView) findViewById(R.id.occasion);
        TextView season = (TextView) findViewById(R.id.season);
        ActionBar actionBar = getSupportActionBar();

        final Intent intent = getIntent();
        actionBar.setTitle(intent.getStringExtra(NAME));
        Glide.with(this).load(intent.getStringExtra(PHOTO_URL)).into(imageView);
        category.setText(intent.getStringExtra(CATEGORY));
        occasion.setText(intent.getStringExtra(OCCASION));
        season.setText(intent.getStringExtra(SEASON));

        int photoId;
        if (!intent.getStringExtra(CATEGORY).contains("-")) {
            photoId = getResources().getIdentifier(intent.getStringExtra(CATEGORY), "drawable",
                    getApplicationContext().getPackageName());
        } else {
            photoId = getResources().getIdentifier(intent.getStringExtra(CATEGORY).replace("-", "_").toLowerCase(),
                    "drawable", getApplicationContext().getPackageName());
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), photoId));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(getApplicationContext(), ConstructorActivity.class);
                mIntent.putExtra(PHOTO_URL, intent.getStringExtra(PHOTO_URL));
                mIntent.putExtra(CATEGORY, intent.getStringExtra(CATEGORY));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), CAMERA) != PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ClothingItemActivity.this, CAMERA)) {
                        new PermissionsHelper().showPermissionDialog(CAMERA_PERMISSION_REQUEST);
                    } else {
                        ActivityCompat.requestPermissions(ClothingItemActivity.this,
                                new String[]{CAMERA}, CAMERA_PERMISSION_REQUEST);
                    }
                } else {
                    startActivity(mIntent);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults[0] == PERMISSION_GRANTED) {
                startActivity(mIntent);
            } else {
                new PermissionsHelper(ClothingItemActivity.this)
                        .showPermissionDialog(CAMERA_PERMISSION_REQUEST);
            }
        }
    }

}
