package charmelinetiel.androidnewsapp.models;

/**
 * Created by Tiel on 29-9-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Password")
    @Expose
    private String password;
    //private String userAuthToken;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


//
//    public String UserAuthToken() {
//        return this.userAuthToken;
//    }
//
//    public void setUserAuthToken(String authToken) {
//        this.userAuthToken = authToken;
//    }

}