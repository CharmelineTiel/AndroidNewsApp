package charmelinetiel.androidnewsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class LikedArticlesActivity extends AppCompatActivity implements
        Callback<RootObject>,
        NewsItemAdapter.NewsItemListener,
        NavigationView.OnNavigationItemSelectedListener
{

    private RecyclerView mRecyclerView;
    private APIService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_articles);


        //menu stuff
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //add items to recyclerview
        mRecyclerView = findViewById(R.id.news_item_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://inhollandbackend.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(APIService.class);

        fetchLikedContent();
    }

    private void fetchLikedContent() {


        mService.getLikedArticles(token.authToken).enqueue(this);

    }

    @Override
    public void onItemClick(View view, Article content) {

        Intent intent = new Intent(this, DetailPageActivity.class);
        intent.putExtra(DetailPageActivity.CONTENT, content);
        intent.setType("text/plain");
        startActivity(intent);
    }
    @Override
    protected void onRestart() {

        super.onRestart();
        Intent i = new Intent(LikedArticlesActivity.this, LikedArticlesActivity.class);
        startActivity(i);
        finish();
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

        Log.e("Failure", "Er is iets fout gegaan, probeer het opnieuw", t);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.refresh) {
            Intent i = new Intent(LikedArticlesActivity.this, LikedArticlesActivity.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {


            Intent intent = new Intent(LikedArticlesActivity.this, MainActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_login) {

            Intent intent = new Intent(LikedArticlesActivity.this, LoginActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_liked) {

            Intent intent = new Intent(LikedArticlesActivity.this, LikedArticlesActivity.class);
            startActivity(intent);

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
