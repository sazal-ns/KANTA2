package com.brosolved.siddiqui.kanta.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class User {

	@SerializedName("data")
	private List<UserInfo> data;

	public void setData(List<UserInfo> data){
		this.data = data;
	}

	public List<UserInfo> getData(){
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