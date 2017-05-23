package not.dresser;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import static not.dresser.MainActivity.NAME;

public class ShelfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelf_activity);
        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        assert actionBar != null;
        actionBar.setTitle(intent.getStringExtra(NAME));
    }
}
