package com.brosolved.siddiqui.kanta.viewModel;

import android.util.Log;

import com.brosolved.siddiqui.kanta.models.Categories;
import com.brosolved.siddiqui.kanta.models.Products;
import com.brosolved.siddiqui.kanta.remote.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";

    public MutableLiveData<Categories> getCategories(){

        Log.i(TAG, "getCategories: "+ Repository.getInstance().loadAllCategories().getValue());
        return Repository.getInstance().loadAllCategories();
    }

    public LiveData<Products> getProducts(){
        return Repository.getInstance().loadAllProducts();
    }
}
