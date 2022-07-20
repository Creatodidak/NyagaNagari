package id.creatodidak.nyaganagari.Models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Pejabat{

	@SerializedName("Pejabat")
	private List<PejabatItem> pejabat;

	public List<PejabatItem> getPejabat(){
		return pejabat;
	}
}