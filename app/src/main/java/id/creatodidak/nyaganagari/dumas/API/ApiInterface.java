package id.creatodidak.nyaganagari.dumas.API;

import com.google.gson.JsonObject;

import java.util.List;

import id.creatodidak.nyaganagari.Models.BeritaItem;
import id.creatodidak.nyaganagari.dumas.Config;
import id.creatodidak.nyaganagari.dumas.Model.Reg;
import id.creatodidak.nyaganagari.dumas.Model.Response;
import id.creatodidak.nyaganagari.dumas.Model.Saran;
import id.creatodidak.nyaganagari.dumas.Model.Update;
import id.creatodidak.nyaganagari.dumas.Model.prog.ProgressItem;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by BRIPDA ANGGI PERIANTO on 11,June,2022 CREATODIDAK anggiperianto41ays@gmail.com
 **/
public interface ApiInterface {

    @FormUrlEncoded
    @POST(Config.REG_URL)
    Call<Reg> reg(
            @Field("nik") String nik,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("hp") String hp,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(Config.EKS_URL)
    Call<Response> cek(
            @Field("nik") String SSnik
    );




    @Multipart
    @POST(Config.UPLOAD_URL)
    Call<JsonObject> uploadPdf(@Part("ff\"; filename=\"file.jpg\" ") RequestBody file,
                               @Part("submit") RequestBody filename,
                               @Part("nik") RequestBody xnik,
                               @Part("nama") RequestBody xnama,
                               @Part("alamat") RequestBody xalamat,
                               @Part("hp") RequestBody xhp,
                               @Part("terlapor") RequestBody xterlapor,
                               @Part("penjelasan") RequestBody xpenjelasan,
                               @Part("jenislaporan") RequestBody xjenislaporan,
                               @Part("jenispelayanan") RequestBody xjenispelayanan);

    @FormUrlEncoded
    @POST(Config.PROGRESS_URL)
    Call<List<ProgressItem>> getProgress(@Field("nama") String nama);

    @FormUrlEncoded
    @POST(Config.SARAN)
    Call<Saran> inputSaran(@Field("nama") String nama,
                           @Field("nik") String nik,
                           @Field("saran") String saran);

    @POST(Config.UPDATE)
    Call<Update> update();


}
