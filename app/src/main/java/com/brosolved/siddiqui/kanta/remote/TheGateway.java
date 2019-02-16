package com.brosolved.siddiqui.kanta.remote;

import androidx.annotation.NonNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * com.brosolved.siddiqui.kanta.remote is created by Noor Nabiul Alam Siddiqui on 2/16/2019
 *
 * BroSolved (c) 2019.
 */
public final class TheGateway {
    private static final String BASE_URL = "http://dev.brosolved.com/kanta/api/";

    @NonNull
    private static Retrofit retrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @NonNull
    public static API path(){
        return retrofit().create(API.class);
    }
}
