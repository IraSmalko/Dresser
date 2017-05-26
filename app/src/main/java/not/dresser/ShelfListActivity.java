package not.dresser;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import io.realm.Realm;

import static not.dresser.MainActivity.NAME;

public class ShelfListActivity extends AppCompatActivity {

    private Realm mRealm;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelf_list_activity);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.shelfListRecyclerView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRealm = Realm.getDefaultInstance();
        mIntent = getIntent();

        getSupportActionBar().setTitle(mIntent.getStringExtra(NAME));

        ShelfListRecyclerAdapter recyclerAdapter = new ShelfListRecyclerAdapter(this, new CRUDRealm(mRealm)
                .getClothingList(mIntent.getStringExtra(NAME)),
                new ShelfListRecyclerAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(ClothingItem item) {
//                        Intent intent = new Intent(getApplicationContext(), AddClothingItemActivity.class);
//                        intent.putExtra(NAME, item.getName());
//                        startActivity(intent);
                    }
                });

        recyclerView.setAdapter(recyclerAdapter);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
