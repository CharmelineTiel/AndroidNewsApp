package charmelinetiel.androidnewsapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import charmelinetiel.androidnewsapp.R;
import charmelinetiel.androidnewsapp.models.Article;
import charmelinetiel.androidnewsapp.models.token;
import charmelinetiel.androidnewsapp.webservice.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailPageActivity extends Activity implements View.OnClickListener{
    public static final String CONTENT = "8b:CONTENT";
    private APIService mService;
    private ImageView faveIcon;
    private Button readMore;
    private Article content;

    public DetailPageActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //start retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://inhollandbackend.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(APIService.class);
//

         this.content = getIntent().getParcelableExtra(CONTENT);

         ImageView image = findViewById(R.id.list_item_image);
        ((TextView) findViewById(R.id.list_item_title)).setText(content.getTitle());
        ((TextView) findViewById(R.id.list_item_description)).setText(content.getSummary());
        ((TextView) findViewById(R.id.date)).setText(content.getPublishDate());
        ((TextView) findViewById(R.id.list_item_title)).setText(content.getTitle());
        this.faveIcon =  findViewById(R.id.likedIcon);
        faveIcon.setOnClickListener(this);
        this.readMore = findViewById(R.id.fullArticle);
        readMore.setOnClickListener(this);
        Glide.with(this).load(content.getImage()).centerCrop().into(image);



        if (token.authToken != null)
        {
            faveIcon.setVisibility(View.VISIBLE);

            if (content.getIsLiked()){

                faveIcon.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
            }else{
                faveIcon.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
            }

        }


    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        finish();
    }
    @Override
    protected void onRestart() {

        super.onRestart();
        Intent i = new Intent(DetailPageActivity.this, DetailPageActivity.class);
        startActivity(i);
        finish();
    }
    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.likedIcon:
            {

                //like
                if(!content.getIsLiked() == true)
                {
                    mService.likeArticle(token.authToken,content.getId()).enqueue(new Callback<Void>(){

                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                            int statusCode = response.code();
                            Toast.makeText(DetailPageActivity.this, "Liked!", Toast.LENGTH_LONG).show();
                            faveIcon.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                            Toast.makeText(DetailPageActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }else{
                    //unlike

                    mService.UnlikeArticle(token.authToken,content.getId()).enqueue(new Callback<Void>(){

                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                            int statusCode = response.code();
                            Toast.makeText(DetailPageActivity.this, "Unliked", Toast.LENGTH_LONG).show();
                            faveIcon.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);


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

                Uri uri = Uri.parse(content.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }
    }
}
