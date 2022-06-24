package id.creatodidak.nyaganagari.Models;

import com.google.gson.annotations.SerializedName;

public class BeritaItem{

	@SerializedName("view")
	private String view;

	@SerializedName("foto")
	private String foto;

	@SerializedName("satuan")
	private String satuan;

	@SerializedName("jam")
	private String jam;

	@SerializedName("publish")
	private String publish;

	@SerializedName("satker")
	private String satker;

	@SerializedName("id")
	private String id;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("judul")
	private String judul;

	@SerializedName("isi")
	private String isi;

	@SerializedName("url")
	private String url;

	public String getView(){
		return view;
	}

	public String getFoto(){
		return foto;
	}

	public String getSatuan(){
		return satuan;
	}

	public String getJam(){
		return jam;
	}

	public String getPublish(){
		return publish;
	}

	public String getSatker(){
		return satker;
	}

	public String getId(){
		return id;
	}

	public String getTanggal(){
		return tanggal;
	}

	public String getJudul(){
		return judul;
	}

	public String getIsi(){
		return isi;
	}

	public String getUrl(){
		return url;
	}
}