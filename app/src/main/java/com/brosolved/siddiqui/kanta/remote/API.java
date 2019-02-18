package com.brosolved.siddiqui.kanta.remote;

import com.brosolved.siddiqui.kanta.models.Categories;
import com.brosolved.siddiqui.kanta.models.Products;

import retrofit2.Call;
import retrofit2.http.GET;

/*
 * com.brosolved.siddiqui.kanta.remote is created by Noor Nabiul Alam Siddiqui on 2/16/2019
 *
 * BroSolved (c) 2019.
 */
public interface API {

    @GET("category")
    Call<Categories> getAllCategoriesWithProducts();

    @GET("product")
    Call<Products> getAllProducts();

}
