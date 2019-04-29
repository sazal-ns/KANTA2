package com.brosolved.siddiqui.kanta;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.brosolved.siddiqui.kanta.app.Config;
import com.brosolved.siddiqui.kanta.fragments.HomeFragment;
import com.brosolved.siddiqui.kanta.fragments.OrderFragment;
import com.brosolved.siddiqui.kanta.fragments.ProfileFragment;
import com.brosolved.siddiqui.kanta.fragments.ShopProductFragment;
import com.brosolved.siddiqui.kanta.fragments.YoutubeFragment;
import com.brosolved.siddiqui.kanta.models.MutableUser;
import com.brosolved.siddiqui.kanta.models.User;
import com.brosolved.siddiqui.kanta.models.UserInfo;
import com.brosolved.siddiqui.kanta.remote.API;
import com.brosolved.siddiqui.kanta.remote.TheGateway;
import com.brosolved.siddiqui.kanta.utils.NotificationUtils;
import com.brosolved.siddiqui.kanta.utils.RuntimePermissionHandler;
import com.brosolved.siddiqui.kanta.utils._Constant;
import com.brosolved.siddiqui.kanta.viewModel.MainViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RuntimePermissionHandler.RuntimePermissionListener {

    private static final String TAG = "MainActivity";

    public static UserInfo userInfo;

    private TextView name, contact;

    private RuntimePermissionHandler handler;
    String token;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handler = new RuntimePermissionHandler(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101, this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //txtMessage.setText(message);
                    Log.d(TAG, "onReceive: "+ message);
                }
            }
        };

        displayFirebaseRegId();


        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final Menu menu= navigationView.getMenu();

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
                        Log.d(TAG, "User ID & Name: "+userInfo.getNotiToken()+" "+userInfo.getName());
                    }
                    if (Integer.parseInt(userInfo.getRememberToken()) == 1) {
                        menu.findItem(R.id.nav_add_product).setVisible(false);
                        menu.findItem(R.id.nav_shop_product).setVisible(false);
                    }else
                        menu.findItem(R.id.nav_buy).setTitle("Sell List");
                    openFragment(new HomeFragment());
                }
            });
        }else {
            mainViewModel.addOrGet(getIntent().getStringExtra(_Constant.INTENT_PHONE_NUMBER), String.valueOf(getIntent().getIntExtra(_Constant.IS_BUYER, 1)), token).observe(this, new Observer<MutableUser>() {
                @Override
                public void onChanged(MutableUser user) {
                   if (user != null) {
                       userInfo = user.getData();
                       name.setText(userInfo.getName());
                       contact.setText(userInfo.getMobile());
                       Log.d(TAG, "User ID & Name: "+userInfo.getNotiToken()+" "+userInfo.getName());
                   }

                    if (Integer.parseInt(userInfo.getRememberToken()) == 1) {
                        menu.findItem(R.id.nav_add_product).setVisible(false);
                        menu.findItem(R.id.nav_shop_product).setVisible(false);
                    }else
                        menu.findItem(R.id.nav_buy).setTitle("Sell List");

                    openFragment(new HomeFragment());
                }
            });
        }

        if (!token.equals(userInfo.getNotiToken())){
            API api = TheGateway.path();
            api.updateUserToken(userInfo.getId(), token).enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    userInfo = response.body();
                }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

    }


    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_order) {
            openFragment(OrderFragment.newInstance(0));
        } else if (id == R.id.nav_processing) {
            openFragment(OrderFragment.newInstance(1));
        } else if (id == R.id.nav_buy) {
            openFragment(OrderFragment.newInstance(2));
        } else if (id == R.id.nav_profile) {
            openFragment(new ProfileFragment());
        }else if (id == R.id.nav_add_product)
            startActivity(new Intent(MainActivity.this, ProductAddActivity.class));
        else if (id == R.id.nav_shop_product)
            openFragment(new ShopProductFragment());
        else if (id == R.id.nav_youtube)
            openFragment(new YoutubeFragment());
        else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
        }

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

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
