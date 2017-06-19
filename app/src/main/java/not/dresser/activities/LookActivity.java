package not.dresser.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import not.dresser.R;
import not.dresser.adapters.LookRecyclerAdapter;
import not.dresser.entity.ClothingLook;
import not.dresser.helpers.CRUDRealm;
import not.dresser.helpers.SwipeHelper;

import static not.dresser.activities.MainActivity.NAME;
import static not.dresser.activities.ShelfListActivity.PHOTO_URL;

public class LookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look_activity);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        SwipeHelper swipeHelper = new SwipeHelper(recyclerView, getApplicationContext());

        LookRecyclerAdapter adapter = new LookRecyclerAdapter(this, new CRUDRealm().getClothingLooks(),
                new LookRecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ClothingLook item) {
                Intent intent = new Intent(getApplicationContext(), LookItemActivity.class);
                intent.putExtra(PHOTO_URL, item.getPhotoUrl());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        swipeHelper.attachSwipeLook();
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }
}
