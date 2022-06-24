package id.creatodidak.nyaganagari.Models;

import com.google.gson.annotations.SerializedName;

public class BacaBerita{

	@SerializedName("penulis")
	private String penulis;

	@SerializedName("view")
	private String view;

	@SerializedName("waktu")
	private String waktu;

	@SerializedName("judul")
	private String judul;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("isi")
	private String isi;

	public String getPenulis(){
		return penulis;
	}

	public String getView(){
		return view;
	}

	public String getWaktu(){
		return waktu;
	}

	public String getJudul(){
		return judul;
	}

	public String getGambar(){
		return gambar;
	}

	public String getIsi(){
		return isi;
	}
}