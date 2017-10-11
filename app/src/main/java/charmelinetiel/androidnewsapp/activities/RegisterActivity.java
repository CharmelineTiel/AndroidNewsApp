package charmelinetiel.androidnewsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import charmelinetiel.androidnewsapp.R;
import charmelinetiel.androidnewsapp.models.ServerResponse;
import charmelinetiel.androidnewsapp.models.User;
import charmelinetiel.androidnewsapp.webservice.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private APIService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://inhollandbackend.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(APIService.class);

        final Button loginBtn = findViewById(R.id.RegisterBtn2);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText username = findViewById(R.id.username2);
                EditText password = findViewById(R.id.password2);

                CreateUser(username.getText().toString(), password.getText().toString());
            }
        });
    }

    private void CreateUser(String username, String pass) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(pass);
        Call<ServerResponse> call = mService.register(user);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    ServerResponse Myresponse = response.body();
                    int statusCode = response.code();
                    Log.d("LoginActivity", "Onresponse: " + statusCode);

                    if(response.body().getSuccess() == false){

                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                    }else{

                        Toast.makeText(RegisterActivity.this, "Uw account is aangemaakt!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }


                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d("Error", t.getMessage());
                Toast.makeText(RegisterActivity.this, "Er is iets fout gegaan, probeer het opnieuw", Toast.LENGTH_LONG).show();
            }


        });
    }
}
