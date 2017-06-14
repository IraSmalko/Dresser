package not.dresser.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jcmore2.collage.CollageView;

import not.dresser.R;
import not.dresser.helpers.CRUDImage;
import not.dresser.helpers.CRUDRealm;

import static not.dresser.activities.ShelfActivity.IMAGE_URL;

public class CollageActivity extends AppCompatActivity {

    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collage_activity);

        CollageView collage = (CollageView) findViewById(R.id.collage);
        Intent intent = getIntent();

        for (String url : intent.getStringArrayExtra(IMAGE_URL)) {
            collage.addCard(new CRUDImage().getBitmap(getContentResolver(), url));
        }

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CRUDRealm().addClothingLook(new CRUDImage()
                        .takeScreenshot(getContentResolver(), mFab, getWindow()));
            }
        });
    }
}
