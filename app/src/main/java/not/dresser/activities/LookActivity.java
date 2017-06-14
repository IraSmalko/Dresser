package not.dresser.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import not.dresser.R;
import not.dresser.adapters.LookRecyclerAdapter;
import not.dresser.helpers.CRUDRealm;
import not.dresser.helpers.SwipeHelper;

public class LookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look_activity);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        SwipeHelper swipeHelper = new SwipeHelper(recyclerView, getApplicationContext());

        LookRecyclerAdapter adapter = new LookRecyclerAdapter(this, new CRUDRealm().getClothingLooks());
        recyclerView.setAdapter(adapter);
        swipeHelper.attachSwipeLook();
    }
}
