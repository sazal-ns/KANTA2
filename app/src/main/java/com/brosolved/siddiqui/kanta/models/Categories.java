package com.brosolved.siddiqui.kanta.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Categories{

	@SerializedName("data")
	private List<Category> data;

	public void setData(List<Category> data){
		this.data = data;
	}

	public List<Category> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"Categories{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}