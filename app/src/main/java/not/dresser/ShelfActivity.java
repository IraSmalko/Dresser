package not.dresser;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import static not.dresser.MainActivity.NAME;

public class ShelfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelf_activity);
        ActionBar actionBar = getSupportActionBar();
        ImageView imageView = (ImageView) findViewById(R.id.photoImageView);

        Intent intent = getIntent();
        assert actionBar != null;
        actionBar.setTitle(intent.getStringExtra(NAME));

        if(!intent.getStringExtra(NAME).equals("T-shirt")){
            int photoId = getResources().getIdentifier(intent.getStringExtra(NAME),"drawable",
                    getApplicationContext().getPackageName());
            imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), photoId));
        }
    }
}
