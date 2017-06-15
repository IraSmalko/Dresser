package not.dresser.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import not.dresser.R;

import static not.dresser.activities.ShelfListActivity.PHOTO_URL;

public class LookItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clothing_item_activity);

        ImageView imageView = (ImageView) findViewById(R.id.photoImageView);
        Intent intent = getIntent();
        Glide.with(this).load(intent.getStringExtra(PHOTO_URL)).into(imageView);
    }
}
