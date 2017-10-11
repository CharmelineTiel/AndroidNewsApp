package charmelinetiel.androidnewsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import charmelinetiel.androidnewsapp.R;
import charmelinetiel.androidnewsapp.adapters.NewsItemAdapter;
import charmelinetiel.androidnewsapp.models.Article;
import charmelinetiel.androidnewsapp.models.RootObject;
import charmelinetiel.androidnewsapp.models.token;
import charmelinetiel.androidnewsapp.webservice.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity implements Callback<RootObject>, NewsItemAdapter.NewsItemListener{

    private RecyclerView mRecyclerView;
    private APIService mService;
    private LinearLayoutManager layout;
    private int totalItemCount;
    private int visibleItemCount;
    private boolean loading = true;
    private int pastVisiblesItems;
    private int nextId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.news_item_view);
        layout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layout);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://inhollandbackend.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(APIService.class);


        fetchContent();

        

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {

                if (loading) {
                    if (dy > 0) //check for scroll down
                    {
                        visibleItemCount = layout.getChildCount();
                        totalItemCount = layout.getItemCount();
                        pastVisiblesItems = layout.findFirstVisibleItemPosition();

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;

                            //Toast.makeText(MainActivity.this, "END", Toast.LENGTH_SHORT).show();

                            //get more content
                            LoadMoreContent(getNextId());
                            loading = true;

                        }

                    }
                }
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onStop() {
        Log.w(TAG, "App stopped");

        super.onStop();
    }

    @Override
    protected void onRestart() {

        super.onRestart();
        Intent i = new Intent(MainActivity.this, MainActivity.class);  //your class
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        Log.w(TAG, "App destroyed");

        super.onDestroy();
    }

    private void fetchContent() {

        Call<RootObject> articles = mService.getAllArticles(token.authToken);
        articles.enqueue(this);

    }

    private void LoadMoreContent(int nextId) {

        int amountOfArticles = 20;
        Call<RootObject> articles = mService.getMoreArticles(token.authToken, nextId, amountOfArticles);
        articles.enqueue(this);

    }

    @Override
    public void onResponse(Call<RootObject> call, Response<RootObject> response) {

        if (response.isSuccessful() && response.body() != null) {
            NewsItemAdapter adapter = new NewsItemAdapter(this, response.body().getResults(), this);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.animate().alpha(1);

            //get next id
            setNextId(response.body().getNextId());
        }
    }

    @Override
    public void onFailure(Call<RootObject> call, Throwable t) {
        Log.e("Exercise6", "Could not fetch data", t);
    }

    @Override
    public void onItemClick(View view, Article content) {

        Intent intent = new Intent(this, DetailPageActivity.class);
        intent.putExtra(DetailPageActivity.CONTENT, content);
        intent.setType("text/plain");
        startActivity(intent);
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }
}
