package id.creatodidak.nyaganagari.Models;

import com.google.gson.annotations.SerializedName;

public class PejabatItem{

	@SerializedName("nama")
	private String nama;

	@SerializedName("foto")
	private String foto;

	@SerializedName("jabatan")
	private String jabatan;

	@SerializedName("jabatanterakhir")
	private String jabatanterakhir;

	@SerializedName("hp")
	private String hp;

	@SerializedName("pangkat")
	private String pangkat;

	@SerializedName("diktuk")
	private String diktuk;

	@SerializedName("id")
	private String id;

	@SerializedName("nrp")
	private String nrp;

	@SerializedName("ttl")
	private String ttl;

	public String getNama(){
		return nama;
	}

	public String getFoto(){
		return foto;
	}

	public String getJabatan(){
		return jabatan;
	}

	public String getJabatanterakhir(){
		return jabatanterakhir;
	}

	public String getHp(){
		return hp;
	}

	public String getPangkat(){
		return pangkat;
	}

	public String getDiktuk(){
		return diktuk;
	}

	public String getId(){
		return id;
	}

	public String getNrp(){
		return nrp;
	}

	public String getTtl(){
		return ttl;
	}
}