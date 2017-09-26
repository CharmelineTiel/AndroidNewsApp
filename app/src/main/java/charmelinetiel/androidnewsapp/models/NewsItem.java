package charmelinetiel.androidnewsapp.models;

import android.content.Context;

import charmelinetiel.androidnewsapp.R;

/**
 * Created by Tiel on 26-9-2017.
 */

public class NewsItem {

    public String Title;
    public String Description;

    public NewsItem(Context context, int id) {

        this.Title = context.getString(R.string.newsItem_title, id);
        this.Description = context.getString(R.string.newsItem_description, id);
    }

}
