package charmelinetiel.androidnewsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import charmelinetiel.androidnewsapp.R;
import charmelinetiel.androidnewsapp.models.AuthTokenResponse;
import charmelinetiel.androidnewsapp.models.User;
import charmelinetiel.androidnewsapp.models.token;
import charmelinetiel.androidnewsapp.webservice.APIService;
import charmelinetiel.androidnewsapp.webservice.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity{

    private APIService mService;
    private User loggedInUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Retrofit retrofit = RetrofitClient.getClient();
        mService = retrofit.create(APIService.class);

        mService = retrofit.create(APIService.class);

        final Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText username = findViewById(R.id.username);
                EditText password = findViewById(R.id.password);

                fetchUser(username.getText().toString(),password.getText().toString());
            }
        });


        final Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchUser(String username, String pass) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(pass);
        setUser(user);
        Call<AuthTokenResponse> call = mService.getAuthToken(user);
        call.enqueue(new Callback<AuthTokenResponse>() {
            @Override
            public void onResponse(Call<AuthTokenResponse> call, Response<AuthTokenResponse> response) {

                int statusCode = response.code();
                if (response.isSuccessful() && statusCode == 200) {

                    AuthTokenResponse authtoken = response.body();

                        Toast.makeText(LoginActivity.this, "Ingelogd", Toast.LENGTH_SHORT).show();
                        token.authToken = authtoken.getAuthToken();

                        Intent intent = new Intent(LoginActivity.this, AllArticlesActivity.class);
                        startActivity(intent);

                    }else{

                    Toast.makeText(LoginActivity.this, "Uw wachtwoord of gebruikernaam is onjuist", Toast.LENGTH_SHORT).show();
                }

         }

            @Override
            public void onFailure(Call<AuthTokenResponse> call, Throwable t) {

                Toast.makeText(LoginActivity.this, "Er is iets fout gegaan, probeer het opnieuw", Toast.LENGTH_LONG).show();
            }


        });
    }


    public User getUser() {
        return this.loggedInUser;
    }

    public void setUser(User user) {
        this.loggedInUser = user;
    }
}
