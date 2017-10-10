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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.news_item_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://inhollandbackend.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(APIService.class);


        fetchContent();

        //TODO:implement scrolling + listener

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
    protected void onDestroy() {
        Log.w(TAG, "App destroyed");

        super.onDestroy();
    }

    private void fetchContent() {


        mService.articles().enqueue(this);

    }


    @Override
    public void onResponse(Call<RootObject> call, Response<RootObject> response) {

        if (response.isSuccessful() && response.body() != null) {
            NewsItemAdapter adapter = new NewsItemAdapter(this, response.body().getResults(), this);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.animate().alpha(1);
        }
    }

    @Override
    public void onFailure(Call<RootObject> call, Throwable t) {
        //Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        Log.e("Exercise6", "Could not fetch data", t);
    }

    @Override
    public void onItemClick(View view, Article content) {

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(SecondActivity.CONTENT, content);
        intent.setType("text/plain");
        startActivity(intent);
    }
}
