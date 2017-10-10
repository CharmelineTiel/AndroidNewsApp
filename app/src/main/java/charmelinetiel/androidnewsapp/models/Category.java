package charmelinetiel.androidnewsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tiel on 29-9-2017.
 */

public class Category implements Parcelable {

    private int Id;

    protected Category(Parcel in) {
        Id = in.readInt();
        Name = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public int getId() { return this.Id; }

    public void setId(int Id) { this.Id = Id; }

    private String Name;

    public String getName() { return this.Name; }

    public void setName(String Name) { this.Name = Name; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Name);
    }
}
