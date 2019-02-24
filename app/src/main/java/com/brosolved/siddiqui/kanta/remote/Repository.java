package com.brosolved.siddiqui.kanta.remote;

import android.util.Log;

import com.brosolved.siddiqui.kanta.models.Categories;
import com.brosolved.siddiqui.kanta.models.MutableUser;
import com.brosolved.siddiqui.kanta.models.Products;
import com.brosolved.siddiqui.kanta.models.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * com.brosolved.siddiqui.kanta.remote is created by Noor Nabiul Alam Siddiqui on 2/23/2019
 *
 * BroSolved (c) 2019.
 */
public class Repository {
    private static final String TAG = "Repository";
    private static final Repository ourInstance = new Repository();

    private API api;

    public static Repository getInstance() {
        return ourInstance;
    }

    private Repository() {
        api = TheGateway.path();
    }

    public LiveData<User> loadUser(String mobile) {
        final MutableLiveData<User> liveData = new MutableLiveData<>();

        Log.i(TAG, "loadUser: "+mobile);
        api.getUserInfo(mobile).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: "+ response.body());
                    liveData.setValue(response.body());
                } else liveData.setValue(null);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                liveData.setValue(null);
                t.printStackTrace();
            }
        });

        return liveData;
    }

    public LiveData<MutableUser> addOrGet(String mobile, String isBuyer) {
        final MutableLiveData<MutableUser> liveData = new MutableLiveData<>();

        api.addOrGetUser(mobile, isBuyer).enqueue(new Callback<MutableUser>() {
            @Override
            public void onResponse(Call<MutableUser> call, Response<MutableUser> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else liveData.setValue(null);

            }

            @Override
            public void onFailure(Call<MutableUser> call, Throwable t) {
                liveData.setValue(null);
                t.printStackTrace();
            }
        });

        return liveData;
    }


    public MutableLiveData<Categories> loadAllCategories() {
        final MutableLiveData<Categories> categoriesMutableLiveData = new MutableLiveData<>();

        api.getAllCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if (response.isSuccessful()) categoriesMutableLiveData.setValue(response.body());
                else categoriesMutableLiveData.setValue(null);
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                categoriesMutableLiveData.setValue(null);
                t.printStackTrace();
            }
        });

        return categoriesMutableLiveData;
    }

    public LiveData<Products> loadAllProducts() {
        final MutableLiveData<Products> productsMutableLiveData = new MutableLiveData<>();

        api.getAllProducts().enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()) productsMutableLiveData.setValue(response.body());
                else productsMutableLiveData.setValue(null);
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                productsMutableLiveData.setValue(null);
                t.printStackTrace();
            }
        });

        return productsMutableLiveData;
    }
}
