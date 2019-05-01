package com.brosolved.siddiqui.kanta.remote;

import com.brosolved.siddiqui.kanta.models.CartProduct;
import com.brosolved.siddiqui.kanta.models.Categories;
import com.brosolved.siddiqui.kanta.models.MSProduct;
import com.brosolved.siddiqui.kanta.models.MutableUser;
import com.brosolved.siddiqui.kanta.models.Product;
import com.brosolved.siddiqui.kanta.models.Products;
import com.brosolved.siddiqui.kanta.models.Rating;
import com.brosolved.siddiqui.kanta.models.Status;
import com.brosolved.siddiqui.kanta.models.User;
import com.brosolved.siddiqui.kanta.models.UserInfo;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    Call<MutableUser> addOrGetUser(@Field("mobile") String mobile, @Field("remember_token") String isBuyer, @Field("notiToken") String notiToken);

    @FormUrlEncoded
    @PUT("user/{id}")
    Call<UserInfo> updateUserInfo(@Path("id") int id, @Field("name") String name, @Field("mobile") String mobile, @Field("shop_name") String shop_name, @Field("address") String address);

    @POST("product")
    Call<Boolean> addProduct(@Body RequestBody file);

    @GET("productByUser/{id}")
    Call<List<Product>> getAllProducts(@Path("id") int id);

    @DELETE("product/{id}")
    Call<Status> deleteProduct(@Path("id") int id);

    @FormUrlEncoded
    @PUT("product/{id}")
    Call<Product> updateProduct(@Path("id") int id, @Field("name") String name, @Field("price") int price, @Field("details") String details, @Field("quantity") int quantity);

    @FormUrlEncoded
    @POST("saveProduct")
    Call<CartProduct> addToCart(@Field("user_id") int user_id, @Field("product_id") int product_id, @Field("quantity") int quantity);

    @GET("saveProduct/{id}")
    Call<List<MSProduct>> orderCondition(@Path("id") int id);

    @FormUrlEncoded
    @PUT("saveProduct")
    Call<CartProduct> updateStatus(@Field("id") int id, @Field("status") int status);

    @GET("saveBuyer/{id}")
    Call<CartProduct> orderBuyer(@Path("id") int id);

    @FormUrlEncoded
    @POST("rating")
    Call<Rating> addRating(@Field("user_id") int user_id, @Field("product_id") int product_id, @Field("rating") String rating);

    @FormUrlEncoded
    @PUT("token/{id}")
    Call<UserInfo> updateUserToken(@Path("id") int id, @Field("email_verified_at") String notiToken);

}
