package not.dresser.activities;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.jcmore2.collage.CollageView;


import not.dresser.R;

public class CollageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collage_activity);

        CollageView collage = (CollageView) findViewById(R.id.collage);
collage.addCard(ContextCompat.getDrawable(getApplicationContext(), R.drawable.t_shirt));
        collage.addCard(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dress));
        collage.addCard(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pants));
//        List<Bitmap> listRes = new ArrayList<Bitmap>();
//        listRes.add();
//        listRes.add()
//        listRes.add(R.drawable.img3);
//        listRes.add(R.drawable.img4);
//
//        collage.createCollageResources(listRes);
    }
}
