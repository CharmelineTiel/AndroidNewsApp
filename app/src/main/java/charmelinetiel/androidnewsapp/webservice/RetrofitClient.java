package charmelinetiel.androidnewsapp.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tiel on 3-10-2017.
 */

public class RetrofitClient {

    public static Retrofit retrofit = null;

    public static Retrofit getClient() {

        String baseUrl = "http://inhollandbackend.azurewebsites.net/api/";

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
