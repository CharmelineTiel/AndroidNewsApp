package charmelinetiel.androidnewsapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import charmelinetiel.androidnewsapp.helpers.NewsItemAdapter;
import charmelinetiel.androidnewsapp.models.NewsItem;


public class MainActivity extends Activity{

    private static final boolean USE_LIST = true;
    private List<NewsItem> mContent;
    private NewsItemAdapter mListAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        RecyclerView list = findViewById(R.id.news_item_view);
        mContent = new ArrayList<>();
        addContent(0);
        mListAdapter = new NewsItemAdapter(mContent,this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mGridLayoutManager = new GridLayoutManager(this, 2);

        list.setLayoutManager(USE_LIST ? mLinearLayoutManager : mGridLayoutManager);
        list.setAdapter(mListAdapter);

    }

    private void addContent(int i) {
    }
}
