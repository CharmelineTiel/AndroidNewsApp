package charmelinetiel.androidnewsapp.webservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tiel on 3-10-2017.
 */

public class RetrofitClient {

    public static Retrofit retrofit = null;


    public static Retrofit getClient(String baseUrl) {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
