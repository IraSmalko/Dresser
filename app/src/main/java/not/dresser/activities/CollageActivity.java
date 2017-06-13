package not.dresser.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import com.jcmore2.collage.CollageView;

import java.io.IOException;

import not.dresser.R;

import static not.dresser.activities.ShelfActivity.IMAGE_URL;

public class CollageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collage_activity);

        CollageView collage = (CollageView) findViewById(R.id.collage);
        Intent intent = getIntent();

        for (String url : intent.getStringArrayExtra(IMAGE_URL)) {
            try {
                collage.addCard(MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(url)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
