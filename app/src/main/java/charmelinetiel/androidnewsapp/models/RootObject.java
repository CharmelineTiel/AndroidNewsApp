package charmelinetiel.androidnewsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootObject {

    @SerializedName("Results")
    @Expose
    private List<Article> results = null;
    @SerializedName("NextId")
    @Expose
    private Integer nextId;

    public List<Article> getResults() {
        return results;
    }

    public void setResults(List<Article> results) {
        this.results = results;
    }

    public Integer getNextId() {
        return nextId;
    }

    public void setNextId(Integer nextId) {
        this.nextId = nextId;
    }

}