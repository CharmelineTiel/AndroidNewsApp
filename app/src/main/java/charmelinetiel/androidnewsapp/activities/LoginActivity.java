package charmelinetiel.androidnewsapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import charmelinetiel.androidnewsapp.R;
import charmelinetiel.androidnewsapp.models.AuthToken;
import charmelinetiel.androidnewsapp.models.User;
import charmelinetiel.androidnewsapp.webservice.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity{

    private APIService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://inhollandbackend.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(APIService.class);

        final Button button = findViewById(R.id.loginBtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText username = findViewById(R.id.username);
                EditText password = findViewById(R.id.username);

                fetchUser(username.getText().toString(),password.getText().toString());
            }
        });
    }



    private void fetchUser(String username, String pass) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(pass);
        Call<AuthToken> call = mService.getAuthToken(user);
        call.enqueue(new Callback<AuthToken>() {
            @Override
            public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {

                if (response.isSuccessful() && response.body() != null) {

                    AuthToken token = response.body();
                    int statusCode = response.code();
                    Log.d("LoginActivity", "Onresponse: " + statusCode);
                    //String header = response.headers().get("x-authtoken");

                }
            }

            @Override
            public void onFailure(Call<AuthToken> call, Throwable t) {

                Log.d("Error", t.getMessage());
            }


        });
    }


}
