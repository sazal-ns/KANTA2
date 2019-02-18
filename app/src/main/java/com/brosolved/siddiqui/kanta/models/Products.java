package com.brosolved.siddiqui.kanta.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Products {

	@SerializedName("data")
	private List<Product> data;

	public void setData(List<Product> data){
		this.data = data;
	}

	public List<Product> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"Products{" + 
			"product = '" + data + '\'' +
			"}";
		}
}