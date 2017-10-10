package charmelinetiel.androidnewsapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import charmelinetiel.androidnewsapp.R;
import charmelinetiel.androidnewsapp.models.Article;


public class SecondActivity extends Activity {
    public static final String CONTENT = "8b:CONTENT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Article content = getIntent().getParcelableExtra(CONTENT);

         ImageView image = findViewById(R.id.list_item_image);
        ((TextView) findViewById(R.id.list_item_title)).setText(content.getTitle());
        ((TextView) findViewById(R.id.list_item_description)).setText(content.getSummary());
        ((TextView) findViewById(R.id.date)).setText(content.getPublishDate());
        ((TextView) findViewById(R.id.list_item_title)).setText(content.getTitle());



        Glide.with(this).load(content.getImage()).centerCrop().into(image);
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        finish();
    }
}
