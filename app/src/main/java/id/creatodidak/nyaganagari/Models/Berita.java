package id.creatodidak.nyaganagari.Models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Berita{

	@SerializedName("Berita")
	private List<BeritaItem> berita;

	public List<BeritaItem> getBerita(){
		return berita;
	}
}