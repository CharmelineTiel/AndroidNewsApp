package charmelinetiel.androidnewsapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import charmelinetiel.androidnewsapp.R;
import charmelinetiel.androidnewsapp.models.Article;
import charmelinetiel.androidnewsapp.models.Token;
import charmelinetiel.androidnewsapp.webservice.APIService;
import charmelinetiel.androidnewsapp.webservice.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DetailPageActivity extends Activity implements View.OnClickListener{
    public static final String CONTENT = "8b:CONTENT";
    private APIService mService;
    private ImageView faveIcon;
    private Button readMore;
    private Article editedArticle;
    private ListView relatedListView;
    private ListView categoriesListView;
    private TextView categoryLbl, relatedArticlesLbl;

    public DetailPageActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Retrofit retrofit = RetrofitClient.getClient();
        mService = retrofit.create(APIService.class);

        this.editedArticle = getIntent().getParcelableExtra(CONTENT);

         ImageView image = findViewById(R.id.list_item_image);
        ((TextView) findViewById(R.id.list_item_title)).setText(editedArticle.getTitle());
        ((TextView) findViewById(R.id.list_item_description)).setText(editedArticle.getSummary());
        relatedListView = findViewById(R.id.related);
        categoriesListView = findViewById(R.id.categories);
        relatedArticlesLbl = findViewById(R.id.relatedLbl);
        categoryLbl = findViewById(R.id.categoriesLbl);

        List<String> relatedArticles = new ArrayList<>();
        relatedArticles.addAll(editedArticle.getRelated());
        ArrayAdapter<String> relatedArticlesAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                relatedArticles );
        relatedListView.setAdapter(relatedArticlesAdapter);

        List<String> categories = new ArrayList<>();
        relatedArticles.addAll(editedArticle.getRelated());
        ArrayAdapter<String> categorieAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                categories );
        categoriesListView.setAdapter(categorieAdapter);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy  HH:mm");
        Date date = new Date();
        try {
            date = sdf.parse(editedArticle.getPublishDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (categoriesListView.getCount() <= 0){
            categoryLbl.setVisibility(View.GONE);
        }else{
            categoryLbl.setVisibility(View.VISIBLE);
        }

        if (relatedListView.getCount() <= 0){
            relatedArticlesLbl.setVisibility(View.GONE);
        }else{
            relatedArticlesLbl.setVisibility(View.VISIBLE);
        }
        ((TextView) findViewById(R.id.date)).setText(sdf.format(date));
        ((TextView) findViewById(R.id.list_item_title)).setText(editedArticle.getTitle());
        this.faveIcon =  findViewById(R.id.likedIcon);
        faveIcon.setOnClickListener(this);
        this.readMore = findViewById(R.id.fullArticle);
        readMore.setOnClickListener(this);
        Glide.with(this).load(editedArticle.getImage()).centerCrop().into(image);


        if (Token.authToken != null)
        {
            faveIcon.setVisibility(View.VISIBLE);

            if (editedArticle.getIsLiked()){

                faveIcon.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
            }else{
                faveIcon.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
            }

        }

    }


    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.likedIcon:
            {

                //like
                if(editedArticle.getIsLiked() == false)
                {
                    mService.likeArticle(Token.authToken, editedArticle.getId()).enqueue(new Callback<Void>(){

                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {


                            Toast.makeText(DetailPageActivity.this, "Liked!", Toast.LENGTH_LONG).show();
                            faveIcon.setBackgroundResource(R.drawable.ic_favorite_black_24dp);

                            for (Article article : AllArticlesActivity.articles)
                            {
                                if (article.getId().equals(editedArticle.getId())){

                                    article.setIsLiked(true);

                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                            Toast.makeText(DetailPageActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }else if (editedArticle.getIsLiked() == true){


                    //unlike
                    mService.UnlikeArticle(Token.authToken, editedArticle.getId()).enqueue(new Callback<Void>(){

                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                            Toast.makeText(DetailPageActivity.this, "Unliked", Toast.LENGTH_LONG).show();
                            faveIcon.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);

                            for (Article article : AllArticlesActivity.articles)
                            {
                                if (article.getId().equals(editedArticle.getId())){

                                    article.setIsLiked(false);

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                            Toast.makeText(DetailPageActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        });

                }

                break;
            }

            case R.id.fullArticle: {

                Uri uri = Uri.parse(editedArticle.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

        }
    }

    @Override
    public void onResume() {super.onResume();}
}
