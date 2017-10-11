package charmelinetiel.androidnewsapp.models;

/**
 * Created by Tiel on 10-10-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AuthTokenResponse {

    @SerializedName("AuthToken")
    @Expose
    private String authToken;
    private static String token;

    public String getAuthToken() {
        return authToken;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}