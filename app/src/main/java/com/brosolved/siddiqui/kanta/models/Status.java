package com.brosolved.siddiqui.kanta.models;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Status {

	@SerializedName("status")
	private boolean status;

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Status{" +
			"status = '" + status + '\'' + 
			"}";
		}
}