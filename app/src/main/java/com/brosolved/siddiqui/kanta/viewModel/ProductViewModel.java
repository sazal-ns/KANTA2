package com.brosolved.siddiqui.kanta.viewModel;

import com.brosolved.siddiqui.kanta.MainActivity;
import com.brosolved.siddiqui.kanta.models.Categories;
import com.brosolved.siddiqui.kanta.models.Product;
import com.brosolved.siddiqui.kanta.models.Status;
import com.brosolved.siddiqui.kanta.remote.Repository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/*
 * com.brosolved.siddiqui.kanta.viewModel is created by Noor Nabiul Alam Siddiqui on 3/29/2019
 *
 * BroSolved (c) 2019.
 */
public class ProductViewModel extends ViewModel {

    public LiveData<Categories> getCategory(){
        return Repository.getInstance().loadAllCategories();
    }

    public LiveData<Boolean> addProduct(Product product){
        return Repository.getInstance().addProduct(product, String.valueOf(MainActivity.userInfo.getId()));
    }

    public LiveData<List<Product>> getProducts(int id){
        return Repository.getInstance().loadAllProducts(id);
    }

    public  LiveData<Status> delete(int id){
        return Repository.getInstance().delteProduct(id);
    }

    public LiveData<Product> update(Product product){
        return Repository.getInstance().updateProduct(product);
    }
}
