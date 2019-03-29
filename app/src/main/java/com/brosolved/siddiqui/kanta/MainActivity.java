package com.brosolved.siddiqui.kanta;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.brosolved.siddiqui.kanta.fragments.HomeFragment;
import com.brosolved.siddiqui.kanta.fragments.ProfileFragment;
import com.brosolved.siddiqui.kanta.models.MutableUser;
import com.brosolved.siddiqui.kanta.models.User;
import com.brosolved.siddiqui.kanta.models.UserInfo;
import com.brosolved.siddiqui.kanta.utils.RuntimePermissionHandler;
import com.brosolved.siddiqui.kanta.utils._Constant;
import com.brosolved.siddiqui.kanta.viewModel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RuntimePermissionHandler.RuntimePermissionListener {

    private static final String TAG = "MainActivity";

    public static UserInfo userInfo;

    private TextView name, contact;

    private RuntimePermissionHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handler = new RuntimePermissionHandler(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101, this);


        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeader = navigationView.getHeaderView(0);
        name = navHeader.findViewById(R.id.name);
        contact = navHeader.findViewById(R.id.contact);

        if (getIntent().getIntExtra(_Constant.IS_BUYER, 1) == 14){
            mainViewModel.getUserInfo(getIntent().getStringExtra(_Constant.INTENT_PHONE_NUMBER)).observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user != null) {
                        userInfo = user.getData().get(0);
                        name.setText(userInfo.getName());
                        contact.setText(userInfo.getMobile());
                    }
                    openFragment(new HomeFragment());
                }
            });
        }else {
            mainViewModel.addOrGet(getIntent().getStringExtra(_Constant.INTENT_PHONE_NUMBER), "1").observe(this, new Observer<MutableUser>() {
                @Override
                public void onChanged(MutableUser user) {
                   if (user != null) {
                       userInfo = user.getData();
                       name.setText(userInfo.getName());
                       contact.setText(userInfo.getMobile());
                   }
                    openFragment(new HomeFragment());
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        if (id == R.id.nav_order) {
            // Handle the camera action
        } else if (id == R.id.nav_processing) {

        } else if (id == R.id.nav_buy) {

        } else if (id == R.id.nav_profile) {
            openFragment(new ProfileFragment());
        }else if (id == R.id.nav_add_product)
            startActivity(new Intent(MainActivity.this, ProductAddActivity.class));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onAllow() {

    }

    @Override
    public void onDeny() {
        handler = new RuntimePermissionHandler(this, new String[]{Manifest.permission.CALL_PHONE}, 101, this);
    }
}
