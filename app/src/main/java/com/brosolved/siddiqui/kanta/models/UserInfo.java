package com.brosolved.siddiqui.kanta.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class UserInfo {

	@SerializedName("address")
	private String  address;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("created_at")
	private String  createdAt;

	@SerializedName("email_verified_at")
	private String notiToken;

	@SerializedName("id")
	private int id;

	@SerializedName("shop_name")
	private String shopName;

	@SerializedName("remember_token")
	private String rememberToken;

	public void setAddress(String  address){
		this.address = address;
	}

	public String  getAddress(){
		return address;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setCreatedAt(String  createdAt){
		this.createdAt = createdAt;
	}

	public Object getCreatedAt(){
		return createdAt;
	}

	public void setNotiToken(String notiToken){
		this.notiToken = notiToken;
	}

	public Object getNotiToken(){
		return notiToken;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setShopName(String shopName){
		this.shopName = shopName;
	}

	public String getShopName(){
		return shopName;
	}

	public void setRememberToken(String rememberToken){
		this.rememberToken = rememberToken;
	}

	public String getRememberToken(){
		return rememberToken;
	}

	@Override
 	public String toString(){
		return 
			"UserInfo{" +
			"address = '" + address + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",name = '" + name + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",email_verified_at = '" + notiToken + '\'' +
			",id = '" + id + '\'' + 
			",shop_name = '" + shopName + '\'' + 
			",remember_token = '" + rememberToken + '\'' + 
			"}";
		}
}