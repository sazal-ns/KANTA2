package com.brosolved.siddiqui.kanta.remote;

import android.util.Log;

import com.brosolved.siddiqui.kanta.models.Categories;
import com.brosolved.siddiqui.kanta.models.MutableUser;
import com.brosolved.siddiqui.kanta.models.Product;
import com.brosolved.siddiqui.kanta.models.Products;
import com.brosolved.siddiqui.kanta.models.Status;
import com.brosolved.siddiqui.kanta.models.User;
import com.brosolved.siddiqui.kanta.models.UserInfo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

        api.getUserInfo(mobile).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
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

    public  LiveData<UserInfo> updateUserInfo(UserInfo userInfo){
        final MutableLiveData<UserInfo> liveData = new MutableLiveData<>();

        api.updateUserInfo(userInfo.getId(), userInfo.getName(), userInfo.getMobile(), userInfo.getShopName(), userInfo.getAddress()).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.isSuccessful()){
                    liveData.setValue(response.body());
                }else liveData.setValue(null);

                //Log.e(TAG, "onResponse: "+ response );
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
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

    public LiveData<List<Product>> loadAllProducts(int id) {
        final MutableLiveData<List<Product>> productsMutableLiveData = new MutableLiveData<>();

        api.getAllProducts(id).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) productsMutableLiveData.setValue(response.body());
                else productsMutableLiveData.setValue(null);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                productsMutableLiveData.setValue(null);
                t.printStackTrace();
            }
        });

        return productsMutableLiveData;
    }

    public LiveData<Boolean> addProduct(Product product, String userID){
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        data.setValue(false);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("name", product.getName());
        builder.addFormDataPart("price", product.getPrice());
        builder.addFormDataPart("details", product.getDetails());
        builder.addFormDataPart("quantity", String.valueOf(product.getQuantity()));
        builder.addFormDataPart("user_id", userID);
        builder.addFormDataPart("product_category_id", product.getProductCategoryId());
        builder.addFormDataPart("image_url_1",product.getImageFile().getName(),RequestBody.create(MediaType.parse("image/*"), product.getImageFile()));
        final MultipartBody requestBody = builder.build();

        api.addProduct(requestBody).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.code() == 200)
                    data.setValue(response.body());
                else {
                    data.setValue(false);
                    Log.e(TAG, "onResponse: "+response.errorBody());
                }

                Log.i(TAG, "onResponse: "+response);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
                data.setValue(false);
            }
        });

        return data;
    }

    public LiveData<Status> delteProduct(int id){
        final  MutableLiveData<Status> data = new MutableLiveData<>();

        api.deleteProduct(id).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.isSuccessful())
                    data.setValue(response.body());
                else
                    data.setValue(null);

            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                data.setValue(null);
                t.printStackTrace();
            }
        });

        return data;
    }

    public LiveData<Product> updateProduct(Product product){
        final MutableLiveData<Product> data = new MutableLiveData<>();

        api.updateProduct(product.getId(), product.getName(), Integer.parseInt(product.getPrice()), product.getDetails(), product.getQuantity()).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful())
                    data.setValue(response.body());
                else
                    data.setValue(null);

                Log.i(TAG, "onResponse: "+response.body());
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

}
