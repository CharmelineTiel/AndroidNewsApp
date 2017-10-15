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

import java.util.ArrayList;

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


public class MainActivity extends AppCompatActivity implements Callback<RootObject>,
        NewsItemAdapter.NewsItemListener,
        NavigationView.OnNavigationItemSelectedListener

{

    private RecyclerView mRecyclerView;
    private APIService mService;
    private LinearLayoutManager layout;
    private int totalItemCount;
    private int visibleItemCount;
    private boolean loading = true;
    private int pastVisiblesItems;
    private int nextId;
    private final int AMOUNT = 20;
    private NewsItemAdapter adapter;
    private int visibleThreshold = 5;
    private int firstVisibleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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


        //fill recyclerview with items
        mRecyclerView = findViewById(R.id.news_item_view);
        layout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layout);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://inhollandbackend.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(APIService.class);

            fetchContent();

        //load more items with listener
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {

                    if (dy > 0) //check for scroll down
                    {
                        visibleItemCount = layout.getChildCount();
                        totalItemCount = layout.getItemCount();
                        pastVisiblesItems = layout.findFirstVisibleItemPosition();
                        firstVisibleItem = layout.findFirstVisibleItemPosition();

                        if (loading) {

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {

                            loading = false;

                        }

                    }
                        if (!loading && (totalItemCount - visibleItemCount)
                                <= (firstVisibleItem + visibleThreshold)) {

                            //get more content
                            LoadMoreContent();
                            loading = true;
                        }
                }

            }
        });


        adapter = new NewsItemAdapter(this, new ArrayList<Article>(), this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.refresh) {

            Intent i = new Intent(MainActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

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


            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_login) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_liked) {

            Intent intent = new Intent(MainActivity.this, LikedArticlesActivity.class);
            startActivity(intent);

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
//        Intent i = new Intent(MainActivity.this, MainActivity.class);
//        startActivity(i);
//        finish();
    }

    private void fetchContent() {

        Call<RootObject> articles = mService.getAllArticles(token.authToken);
        articles.enqueue(this);

    }
    private void LoadMoreContent() {

        Call<RootObject> articles = mService.getMoreArticles(token.authToken, getNextId(), AMOUNT);
        articles.enqueue(this);

    }

    @Override
    public void onResponse(Call<RootObject> call, Response<RootObject> response) {

        if (response.isSuccessful() && response.body() != null) {

            if (nextId != response.body().getNextId()) {

                setNextId(response.body().getNextId());
                adapter.setmItems(response.body().getResults());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onFailure(Call<RootObject> call, Throwable t) {
        Log.e("Failure", "Er iets fout gegaan, probeer het opnieuw", t);
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
