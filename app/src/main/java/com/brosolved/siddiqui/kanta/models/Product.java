package com.brosolved.siddiqui.kanta.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Product implements Parcelable {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("details")
	private String details;

	@SerializedName("id")
	private int id;

	@SerializedName("image_url_1")
	private String imageUrl1;

	@SerializedName("image_url_3")
	private String imageUrl3;

	@SerializedName("image_url_2")
	private String imageUrl2;

	@SerializedName("product_category_id")
	private String productCategoryId;

	private Product(Parcel source) {
		updatedAt = source.readString();
		userId = source.readString();
		price = source.readString();
		name = source.readString();
		createdAt = source.readString();
		details = source.readString();
		id = source.readInt();
		imageUrl1 = source.readString();
		imageUrl2 = source.readString();
		imageUrl3 = source.readString();
		productCategoryId = source.readString();
	}

	public void setUpdatedAt(String  updatedAt){
		this.updatedAt = updatedAt;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setDetails(String details){
		this.details = details;
	}

	public String getDetails(){
		return details;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setImageUrl1(String imageUrl1){
		this.imageUrl1 = imageUrl1;
	}

	public String getImageUrl1(){
		return imageUrl1;
	}

	public void setImageUrl3(String imageUrl3){
		this.imageUrl3 = imageUrl3;
	}

	public String getImageUrl3(){
		return imageUrl3;
	}

	public void setImageUrl2(String imageUrl2){
		this.imageUrl2 = imageUrl2;
	}

	public String getImageUrl2(){
		return imageUrl2;
	}

	public void setProductCategoryId(String productCategoryId){
		this.productCategoryId = productCategoryId;
	}

	public String getProductCategoryId(){
		return productCategoryId;
	}

	public static final Parcelable.Creator<Product> CREATOR = new Creator<Product>() {
		@Override
		public Product createFromParcel(Parcel source) {
			return new Product(source);
		}

		@Override
		public Product[] newArray(int size) {
			return new Product[size];
		}
	};

	@Override
 	public String toString(){
		return 
			"Product{" +
			"updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",price = '" + price + '\'' + 
			",name = '" + name + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",details = '" + details + '\'' + 
			",id = '" + id + '\'' + 
			",image_url_1 = '" + imageUrl1 + '\'' + 
			",image_url_3 = '" + imageUrl3 + '\'' + 
			",image_url_2 = '" + imageUrl2 + '\'' + 
			",product_category_id = '" + productCategoryId + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(updatedAt);
		dest.writeString(userId);
		dest.writeString(price);
		dest.writeString(name);
		dest.writeString(createdAt);
		dest.writeString(details);
		dest.writeInt(id);
		dest.writeString(imageUrl1);
		dest.writeString(imageUrl2);
		dest.writeString(imageUrl3);
		dest.writeString(productCategoryId);
	}
}