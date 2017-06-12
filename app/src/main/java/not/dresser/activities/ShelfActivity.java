package not.dresser.activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import not.dresser.R;
import not.dresser.adapters.CourseRVAdapter;
import not.dresser.entity.ClothingItem;
import not.dresser.helpers.CRUDRealm;
import not.dresser.helpers.PhotoLoaderAsyncTask;

public class ShelfActivity extends AppCompatActivity {

    private CourseRVAdapter adapter, a, adapter3;
    private RecyclerViewPager coursesRecyclerView, coursesRecyclerView2, coursesRecyclerView3;
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelf_activity);
        imageView = (ImageView) findViewById(R.id.q);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        coursesRecyclerView = (RecyclerViewPager) findViewById(R.id.vertical_courses_list);
        coursesRecyclerView2 = (RecyclerViewPager) findViewById(R.id.vertical_courses);
        coursesRecyclerView3 = (RecyclerViewPager) findViewById(R.id.vertical);
        coursesRecyclerView.getLayoutParams().height = screenHeight / 2;
        coursesRecyclerView2.getLayoutParams().height = screenHeight / 2;
        coursesRecyclerView3.getLayoutParams().height = screenHeight / 2;
        coursesRecyclerView.setHasFixedSize(true);
        coursesRecyclerView2.setHasFixedSize(true);
        coursesRecyclerView3.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager llm2 = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager llm3 = new LinearLayoutManager(getApplicationContext());
        coursesRecyclerView.setLayoutManager(llm);
        coursesRecyclerView2.setLayoutManager(llm2);
        coursesRecyclerView3.setLayoutManager(llm3);

        adapter = new CourseRVAdapter(getApplicationContext(), getResources().getStringArray(R.array.category));
        coursesRecyclerView.setAdapter(adapter);
        a = new CourseRVAdapter(getApplicationContext(), getResources().getStringArray(R.array.category));
        coursesRecyclerView2.setAdapter(a);
        adapter3 = new CourseRVAdapter(getApplicationContext(), getResources().getStringArray(R.array.category));
        coursesRecyclerView3.setAdapter(adapter3);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(getApplicationContext(), CollageActivity.class));
                getBitmapOfCurrentPosition(getApplicationContext(), adapter.getPosition());
            }
        });

//        ImageButton imageButton = (ImageButton) findViewById(R.id.imageBtn);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int numberOfFragment = 1;
//                switch (numberOfFragment) {
//                    case 1:
//                        initializingFragments(R.id.frgm1, new ImageFragment());
//                        ++numberOfFragment;
//                        break;
//                    case 2:
//                        initializingFragments(R.id.frgm2, new ImageFragment());
//                        ++numberOfFragment;
//                        break;
//                    case 3:
//                        initializingFragments(R.id.frgm3, new ImageFragment());
//                        break;
//                }
//
//            }
//        });
    }

    private void initializingFragments(int fragmentContainer, Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void getBitmapOfCurrentPosition(Context context, int position) {
        ClothingItem clothingItem = new CRUDRealm().getClothingList(adapter
                .getmData()[coursesRecyclerView.getCurrentPosition()]).get(position);
        new PhotoLoaderAsyncTask(context, new PhotoLoaderAsyncTask.PhotoLoadProcessed() {
            @Override
            public void onBitmapReady(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }).execute(clothingItem.getPhotoUrl());

    }
}
