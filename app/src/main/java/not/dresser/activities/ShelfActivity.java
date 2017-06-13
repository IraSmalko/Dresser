package not.dresser.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import not.dresser.R;
import not.dresser.adapters.ShelfListRecyclerAdapter;
import not.dresser.entity.ClothingItem;
import not.dresser.helpers.CRUDRealm;

public class ShelfActivity extends AppCompatActivity {

    public static final String IMAGE_URL = "imageUrl";

    private RecyclerViewPager coursesRecyclerView;
    private ImageView imageView, imageView2;
    private int numberImage;
    private String[] imageUrl = new String[2];
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelf_activity);

        imageView = (ImageView) findViewById(R.id.q);
        imageView2 = (ImageView) findViewById(R.id.imageView);
        Spinner spinner = (android.widget.Spinner) findViewById(R.id.spinner);
        coursesRecyclerView = (RecyclerViewPager) findViewById(R.id.vertical_courses_list);
        coursesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        coursesRecyclerView.setLayoutManager(llm);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.category));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                coursesRecyclerView.setVisibility(View.VISIBLE);
                ShelfListRecyclerAdapter adapter = new ShelfListRecyclerAdapter(getApplicationContext(),
                        new CRUDRealm().getClothingList(adapterView.getItemAtPosition(i).toString()),
                        new ShelfListRecyclerAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(ClothingItem item) {
                        switch (numberImage) {
                            case 0:
                                Glide.with(getApplicationContext()).load(item.getPhotoUrl()).into(imageView);
                                imageUrl[0] = item.getPhotoUrl();
                                ++numberImage;
                                break;
                            case 1:
                                Glide.with(getApplicationContext()).load(item.getPhotoUrl()).into(imageView2);
                                imageUrl[1] = item.getPhotoUrl();

                                break;
                        }
                    }
                });
                coursesRecyclerView.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(getApplicationContext(), CollageActivity.class);
                mIntent.putExtra(IMAGE_URL, imageUrl);
                startActivity(mIntent);
            }
        });
    }
}
