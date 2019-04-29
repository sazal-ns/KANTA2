package com.brosolved.siddiqui.kanta.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Rating{

	@SerializedName("data")
	private RatingData data;

	public void setData(RatingData data){
		this.data = data;
	}

	public RatingData getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"Rating{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}