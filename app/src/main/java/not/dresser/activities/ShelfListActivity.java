package not.dresser.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import not.dresser.helpers.CRUDRealm;
import not.dresser.R;
import not.dresser.adapters.ShelfListRecyclerAdapter;
import not.dresser.entity.ClothingItem;
import not.dresser.helpers.SwipeHelper;

import static not.dresser.activities.MainActivity.NAME;

public class ShelfListActivity extends AppCompatActivity {

    public static final String PHOTO_URL = "photoUrl";
    public static final String CATEGORY = "category";
    public static final String OCCASION = "occasion";
    public static final String SEASON = "season";

    private Intent mIntent;
    private List<ClothingItem> mClothingItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelf_list_activity);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.shelfListRecyclerView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SwipeHelper swipeHelper = new SwipeHelper(recyclerView, getApplicationContext());

        mIntent = getIntent();

        getSupportActionBar().setTitle(mIntent.getStringExtra(NAME));
        mClothingItems = new CRUDRealm().getClothingList(mIntent.getStringExtra(NAME));

        ShelfListRecyclerAdapter recyclerAdapter = new ShelfListRecyclerAdapter(this, mClothingItems,
                new ShelfListRecyclerAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(ClothingItem item) {
                        Intent intent = new Intent(getApplicationContext(), ClothingItemActivity.class);
                        intent.putExtra(NAME, item.getName());
                        intent.putExtra(PHOTO_URL, item.getPhotoUrl());
                        intent.putExtra(CATEGORY, item.getCategory());
                        intent.putExtra(OCCASION, item.getOccasion());
                        intent.putExtra(SEASON, item.getSeason());
                        startActivity(intent);
                    }
                });

        recyclerView.setAdapter(recyclerAdapter);
        swipeHelper.attachSwipeCategory();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddClothingItemActivity.class);
                intent.putExtra(NAME, mIntent.getStringExtra(NAME));
                startActivity(intent);
            }
        });
    }
}
