package com.brosolved.siddiqui.kanta;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.brosolved.siddiqui.kanta.adapter.CategoryAdapter;
import com.brosolved.siddiqui.kanta.adapter.ProductsAdapter;
import com.brosolved.siddiqui.kanta.models.Categories;
import com.brosolved.siddiqui.kanta.models.Category;
import com.brosolved.siddiqui.kanta.models.Product;
import com.brosolved.siddiqui.kanta.models.Products;
import com.brosolved.siddiqui.kanta.remote.API;
import com.brosolved.siddiqui.kanta.remote.TheGateway;
import com.brosolved.siddiqui.kanta.utils.CommonTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private List<Category> categories;
    private List<Product> products;

    private CategoryAdapter categoryAdapter;
    private ProductsAdapter productsAdapter;

    private ProgressBar progressBar;
    private RecyclerView productRecyclerView;
    private SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar);
        productRecyclerView = findViewById(R.id.recyclerViewProduct);
        refreshLayout = findViewById(R.id.refresh);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fetchData();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });
    }

    private void initCategories(){
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recycler = findViewById(R.id.recyclerViewCategories);
        recycler.setLayoutManager(manager);
        categoryAdapter = new CategoryAdapter(this, categories);
        recycler.setAdapter(categoryAdapter);
    }

    private void initProducts(){

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            productRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        else productRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        productRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productsAdapter = new ProductsAdapter(this, products);
        productRecyclerView.setAdapter(productsAdapter);
    }

    private void fetchData(){
        API api = TheGateway.path();
        categories = new ArrayList<>();
        products = new ArrayList<>();

        api.getAllCategoriesWithProducts().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if (response.isSuccessful() && response.code() ==200){
                    categories.addAll(response.body().getData());
                    initCategories();
                }else CommonTask.showToast(MainActivity.this, "Something Wrong !!!");

                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                t.printStackTrace();
                CommonTask.showToast(MainActivity.this, "Can't reach to server");
            }

        });

        api.getAllProducts().enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful() && response.code() ==200){
                    products.addAll(response.body().getData());
                    initProducts();
                    progressBar.setVisibility(View.GONE);
                }else {CommonTask.showToast(MainActivity.this, "Something Wrong !!!");
                    progressBar.setVisibility(View.GONE);
                }

                refreshLayout.setRefreshing(false);
                productsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                t.printStackTrace();
                CommonTask.showToast(MainActivity.this, "Can't reach to server");
                progressBar.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            productRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            productRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }
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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
