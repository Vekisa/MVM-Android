package com.example.mvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.navigation.NavigationView;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    protected NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.activity_navigation);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_category, R.id.nav_discussion)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        int checkedItem = navigationView.getCheckedItem().getItemId();
        System.out.println(checkedItem);

        if (checkedItem != id) {


            switch (id) {

                case R.id.nav_profile:
                    Intent h = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(h);
                    break;
                case R.id.nav_category:
                    Intent i = new Intent(getApplicationContext(), CategoryActivity.class);
                    startActivity(i);
                    break;
                case R.id.nav_forum:
                    Intent forum = new Intent(getApplicationContext(), ForumActivity.class);
                    startActivity(forum);
                    break;
                case R.id.nav_suggestion_place:
                    Intent place = new Intent(getApplicationContext(), PurchasePlaceSuggesting.class);
                    startActivity(place);
                    break;
                case R.id.nav_suggestion_production:
                    Intent prod = new Intent(getApplicationContext(), ProductionSuggesting.class);
                    startActivity(prod);
                    break;
                case R.id.nav_offer:
                    Intent offer = new Intent(getApplicationContext(), PriceOffer.class);
                    startActivity(offer);
                    break;
                case R.id.nav_discussion:
                    Intent discussion = new Intent(getApplicationContext(), MessagesActivity.class);
                    startActivity(discussion);
                    break;
                case R.id.map:
                    Intent map = new Intent(getApplicationContext(), Map.class);
                    map.putExtra("value", "map");
                    startActivity(map);
                    break;
                case R.id.nav_predicted_price:
                    Intent mapPP = new Intent(getApplicationContext(), Map.class);
                    mapPP.putExtra("value", "pp");
                    startActivity(mapPP);
                    break;
                case R.id.nav_logout:
                    Intent logout = new Intent(getApplicationContext(), LogoutActivity.class);
                    startActivity(logout);
                    break;
                case R.id.nav_settings:
                    Intent settings = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(settings);
                    break;
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
