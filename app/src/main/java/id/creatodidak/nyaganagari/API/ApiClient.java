package id.creatodidak.nyaganagari.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BRIPDA ANGGI PERIANTO on 11,June,2022 CREATODIDAK anggiperianto41ays@gmail.com
 **/
public class ApiClient {

    private static final String BASE_URL = "https://polreslandak.id/";

    private static Retrofit retrofit;

    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }


}
