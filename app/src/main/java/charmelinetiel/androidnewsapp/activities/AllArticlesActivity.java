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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import charmelinetiel.androidnewsapp.R;
import charmelinetiel.androidnewsapp.adapters.NewsItemAdapter;
import charmelinetiel.androidnewsapp.models.Article;
import charmelinetiel.androidnewsapp.models.RootObject;
import charmelinetiel.androidnewsapp.models.Token;
import charmelinetiel.androidnewsapp.webservice.APIService;
import charmelinetiel.androidnewsapp.webservice.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AllArticlesActivity extends AppCompatActivity implements Callback<RootObject>,
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
    private Retrofit retrofit;
    private final int AMOUNT = 20;
    private NewsItemAdapter adapter;
    private int visibleThreshold = 5;
    private int firstVisibleItem;
    public static List<Article> articles;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //menu stuff
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Home");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //fill recyclerview with items
        mRecyclerView = findViewById(R.id.news_item_view);
        progressBar = findViewById(R.id.progressbar);
        layout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layout);


            retrofit = RetrofitClient.getClient();
            mService = retrofit.create(APIService.class);


            articles = new ArrayList<>();
            adapter = new NewsItemAdapter(this, articles, this);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.invalidate();
            mRecyclerView.animate().alpha(1);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.refresh) {

            Intent intent = new Intent(this, this.getClass());
            startActivity(intent);
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

            finish();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent intent = new Intent(AllArticlesActivity.this, AllArticlesActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_login) {

                Intent intent = new Intent(AllArticlesActivity.this, LoginActivity.class);
                startActivity(intent);

        }
        else if (id == R.id.nav_liked) {

            Intent intent = new Intent(AllArticlesActivity.this, LikedArticlesActivity.class);
            startActivity(intent);

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void fetchContent() {

            Call<RootObject> articles = mService.getAllArticles(Token.authToken);
            articles.enqueue(this);

    }
    private void LoadMoreContent() {

            Call<RootObject> articles = mService.getMoreArticles(Token.authToken, getNextId(), AMOUNT);
            articles.enqueue(this);

    }

    @Override
    public void onResponse(Call<RootObject> call, Response<RootObject> response) {

        showProgressbar();

        if (response.isSuccessful() && response.body() != null) {

            if (nextId != response.body().getNextId()) {

                articles.addAll(response.body().getResults());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        adapter.notifyDataSetChanged();
                    }
                });
                setNextId(response.body().getNextId());
            }
        }else{


            Toast.makeText(this, "Er is iets fout gegaan bij het laden van alle artikelen", Toast.LENGTH_SHORT).show();

        }

        hideProgressbar();
    }

    @Override
    public void onFailure(Call<RootObject> call, Throwable t) {
        Log.e("Failure", "Er iets fout gegaan, probeer het opnieuw", t);
    }
    @Override
    public void onItemClick(View view, Article content) {

        Intent intent = new Intent(this, DetailPageActivity.class);
        intent.putExtra(DetailPageActivity.CONTENT, content);
        startActivity(intent);
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }


    @Override
    public void onRestart() {

        super.onRestart();

        Intent intent = new Intent(this, this.getClass());
        startActivity(intent);

    }


    private void showProgressbar(){

        mRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressbar(){

        mRecyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

    }
}
