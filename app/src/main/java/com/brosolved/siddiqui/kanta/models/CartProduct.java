package com.brosolved.siddiqui.kanta.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CartProduct{

	@SerializedName("data")
	private List<MSProduct> data;

	@SerializedName("error")
	private String error;

	public void setData(List<MSProduct> data){
		this.data = data;
	}

	public List<MSProduct> getData(){
		return data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
 	public String toString(){
		return 
			"CartProduct{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}