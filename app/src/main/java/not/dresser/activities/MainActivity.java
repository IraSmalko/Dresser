package not.dresser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import not.dresser.R;
import not.dresser.adapters.CategoryRecyclerAdapter;
import not.dresser.entity.Category;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.categoryRecyclerView);
        CategoryRecyclerAdapter recyclerAdapter = new CategoryRecyclerAdapter(this, addCategoryList(),
                new CategoryRecyclerAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(Category item) {
                        Intent intent = new Intent(getApplicationContext(), ShelfListActivity.class);
                        intent.putExtra(NAME, item.getName());
                        startActivity(intent);
                    }
                });

        recyclerView.setAdapter(recyclerAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(this, ShelfActivity.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this, LookActivity.class));
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public List<Category> addCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        int[] categoryImage = {R.drawable.t_shirt, R.drawable.pants, R.drawable.dress,
                R.drawable.shorts, R.drawable.skirt, R.drawable.shirt, R.drawable.jacket};
        for (int i = 0; i < getResources().getStringArray(R.array.category).length; i++) {
            Category category = new Category();
            category.setName(getResources().getStringArray(R.array.category)[i]);
            category.setPhoto(ContextCompat.getDrawable(getApplicationContext(), categoryImage[i]));
            categoryList.add(category);
        }
        return categoryList;
    }
}
