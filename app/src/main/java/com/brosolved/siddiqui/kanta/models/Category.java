package com.brosolved.siddiqui.kanta.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Category {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("logo")
	private String logo;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("products")
	private List<Products> products;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return logo;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setProducts(List<Products> products){
		this.products = products;
	}

	public List<Products> getProducts(){
		return products;
	}

	@Override
 	public String toString(){
		return 
			"Category{" +
			"updated_at = '" + updatedAt + '\'' + 
			",logo = '" + logo + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",products = '" + products + '\'' + 
			"}";
		}
}