package charmelinetiel.androidnewsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tiel on 29-9-2017.
 */

public class Article implements Parcelable {

        @SerializedName("Id")
        @Expose
        private String id;
        @SerializedName("Feed")
        @Expose
        private Integer feed;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Summary")
        @Expose
        private String summary;
        @SerializedName("PublishDate")
        @Expose
        private String publishDate;
        @SerializedName("Image")
        @Expose
        private String image;
        @SerializedName("Url")
        @Expose
        private String url;
        @SerializedName("Related")
        @Expose
        private List<String> related = null;
        @SerializedName("Categories")
        @Expose
        private List<Category> categories = null;
        @SerializedName("IsLiked")
        @Expose
        private Boolean isLiked;
        private final static long serialVersionUID = 4399956632668004224L;

    protected Article(Parcel in) {
        title = in.readString();
        summary = in.readString();
        publishDate = in.readString(); //
        image = in.readString();
        url = in.readString();
        related = in.createStringArrayList();
        categories = in.createTypedArrayList(Category.CREATOR);
        id = in.readString();
        isLiked = Boolean.valueOf(in.readString());
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getFeed() {
            return feed;
        }

        public void setFeed(Integer feed) {
            this.feed = feed;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(String publishDate) {
            this.publishDate = publishDate;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<String> getRelated() {
            return related;
        }

        public void setRelated(List<String> related) {
            this.related = related;
        }

        public List<Category> getCategories() {
            return categories;
        }

        public void setCategories(List<Category> categories) {
            this.categories = categories;
        }

        public Boolean getIsLiked() {
            return isLiked;
        }

        public void setIsLiked(Boolean isLiked) {
            this.isLiked = isLiked;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(summary);
        dest.writeString(publishDate);
        dest.writeString(image);
        dest.writeString(url);
        dest.writeStringList(related);
        dest.writeTypedList(categories);
        dest.writeString(id);
        dest.writeString(isLiked.toString());
    }

}
