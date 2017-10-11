package charmelinetiel.androidnewsapp.webservice;

import charmelinetiel.androidnewsapp.models.AuthTokenResponse;
import charmelinetiel.androidnewsapp.models.RootObject;
import charmelinetiel.androidnewsapp.models.ServerResponse;
import charmelinetiel.androidnewsapp.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by
 * Tiel on 29-9-2017.
 */

public interface APIService {



        @GET("Articles")
        Call<RootObject> getAllArticles(@Header("x-authtoken") String api_token);

        @GET("Articles/{nextId}")
        Call<RootObject> getMoreArticles(@Header("x-authtoken") String api_token, @Path("nextId") int nextId, @Query("count") int count);


        @GET("Articles/Liked")
        Call<RootObject> getLikedArticles(@Header("x-authtoken") String api_token);

        @DELETE("Articles/{Id}/like")
        Call<Void> UnlikeArticle(@Header("x-authtoken") String api_token, @Path("Id") String id);

        @PUT("Articles/{Id}/like")
        Call<Void> likeArticle(@Header("x-authtoken") String api_token, @Path("Id") String id);

        @POST("Users/register")
        Call <ServerResponse> register (@Body User user);

        @POST("Users/login")
        Call<AuthTokenResponse> getAuthToken(@Body User user);
}

