package charmelinetiel.androidnewsapp.models;

/**
 * Created by Tiel on 11-10-2017.
 */

public class Token {


    public static String authToken;
    private static Token sInstance;

    public static Token getInstance() {
        if (sInstance == null) {
            sInstance = new Token();
        }

        return sInstance;
    }
    private Token() {

    }

    public void setAuthToken(String authToken){

        this.authToken = authToken;
    }

}
