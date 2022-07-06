package id.creatodidak.nyaganagari.Models;

import com.google.gson.annotations.SerializedName;

public class UpdateLoc{

	@SerializedName("pesan")
	private String pesan;

	public String getPesan(){
		return pesan;
	}
}