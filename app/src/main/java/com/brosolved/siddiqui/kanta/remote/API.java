package com.brosolved.siddiqui.kanta.remote;

import com.brosolved.siddiqui.kanta.models.Categories;
import com.brosolved.siddiqui.kanta.models.MutableUser;
import com.brosolved.siddiqui.kanta.models.Products;
import com.brosolved.siddiqui.kanta.models.User;
import com.brosolved.siddiqui.kanta.models.UserInfo;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/*
 * com.brosolved.siddiqui.kanta.remote is created by Noor Nabiul Alam Siddiqui on 2/16/2019
 *
 * BroSolved (c) 2019.
 */
public interface API {

    @GET("category")
    Call<Categories> getAllCategories();

    @GET("product")
    Call<Products> getAllProducts();

    @GET("user/{number}")
    Call<User> getUserInfo(@Path("number") String number);

    @FormUrlEncoded
    @POST("user")
    Call<MutableUser> addOrGetUser(@Field("mobile") String mobile, @Field("remember_token") String isBuyer);

    @FormUrlEncoded
    @PUT("user/{id}")
    Call<UserInfo> updateUserInfo(@Path("id") int id, @Field("name") String name, @Field("mobile") String mobile, @Field("shop_name") String shop_name, @Field("address") String address);

    @POST("product")
    Call<Boolean> addProduct(@Body RequestBody file);

}
