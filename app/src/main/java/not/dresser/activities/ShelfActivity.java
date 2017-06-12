package not.dresser.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import not.dresser.adapters.CourseRVAdapter;
import not.dresser.R;

public class ShelfActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelf_activity);

        RecyclerView coursesRecyclerView = (RecyclerView) findViewById(R.id.vertical_courses_list);
        coursesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        coursesRecyclerView.setLayoutManager(llm);

        CourseRVAdapter adapter = new CourseRVAdapter(getApplicationContext(), getResources().getStringArray(R.array.category));
        coursesRecyclerView.setAdapter(adapter);

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
}
