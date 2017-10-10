package charmelinetiel.androidnewsapp.webservice;

import java.util.List;

import charmelinetiel.androidnewsapp.models.AuthToken;
import charmelinetiel.androidnewsapp.models.Category;
import charmelinetiel.androidnewsapp.models.RootObject;
import charmelinetiel.androidnewsapp.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by
 * Tiel on 29-9-2017.
 */

public interface APIService {

        @GET("Articles")
        Call<RootObject> articles();

        @GET("feeds")
        Call<List<Category>> feeds();

        @FormUrlEncoded
        @POST("Users/register")
        Call <User> register (@Body User user);

        @POST("Users/login")
        Call<AuthToken> getAuthToken(@Body User user);
}

