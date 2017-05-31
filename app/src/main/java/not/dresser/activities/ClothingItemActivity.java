package not.dresser.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import not.dresser.R;

import static not.dresser.activities.ShelfListActivity.OCCASION;
import static not.dresser.activities.ShelfListActivity.PHOTO_URL;
import static not.dresser.activities.ShelfListActivity.CATEGORY;
import static not.dresser.activities.ShelfListActivity.SEASON;

public class ClothingItemActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clothing_item_activity);

        ImageView imageView = (ImageView) findViewById(R.id.photoImageView);
        TextView category = (TextView) findViewById(R.id.category);
        TextView occasion = (TextView) findViewById(R.id.occasion);
        TextView season = (TextView) findViewById(R.id.season);

        final Intent intent = getIntent();
        Glide.with(this).load(intent.getStringExtra(PHOTO_URL)).into(imageView);
        category.setText(intent.getStringExtra(CATEGORY));
        occasion.setText(intent.getStringExtra(OCCASION));
        season.setText(intent.getStringExtra(SEASON));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), ConstructorActivity.class);
                intent1.putExtra(PHOTO_URL, intent.getStringExtra(PHOTO_URL));
                intent1.putExtra(CATEGORY, intent.getStringExtra(CATEGORY));
                startActivity(intent1);
            }
        });

    }

}
