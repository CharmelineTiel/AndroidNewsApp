package charmelinetiel.androidnewsapp.models;

/**
 * Created by Tiel on 10-10-2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthToken implements Parcelable {

    @SerializedName("AuthToken")
    @Expose
    private String authToken;
    public final static Parcelable.Creator<AuthToken> CREATOR = new Creator<AuthToken>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AuthToken createFromParcel(Parcel in) {
            return new AuthToken(in);
        }

        public AuthToken[] newArray(int size) {
            return (new AuthToken[size]);
        }

    };

    protected AuthToken(Parcel in) {
        this.authToken = ((String) in.readValue((String.class.getClassLoader())));
    }

    public AuthToken() {
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(authToken);
    }

    public int describeContents() {
        return 0;
    }
}