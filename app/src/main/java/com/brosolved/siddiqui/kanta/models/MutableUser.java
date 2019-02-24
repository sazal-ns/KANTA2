package com.brosolved.siddiqui.kanta.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/*
 * com.brosolved.siddiqui.kanta.models is created by Noor Nabiul Alam Siddiqui on 2/24/2019
 *
 * BroSolved (c) 2019.
 */
@Generated("com.robohorse.robopojogenerator")
public class MutableUser {

    @SerializedName("data")
    private UserInfo data;

    public void setData(UserInfo data){
        this.data = data;
    }

    public UserInfo getData(){
        return data;
    }

    @Override
    public String toString(){
        return
                "User{" +
                        "data = '" + data + '\'' +
                        "}";
    }
}
