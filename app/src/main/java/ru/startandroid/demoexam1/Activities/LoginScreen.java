package ru.startandroid.demoexam1.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.startandroid.demoexam1.Api.ApiClient;
import ru.startandroid.demoexam1.LoginFiles.LoginRequest;
import ru.startandroid.demoexam1.LoginFiles.LoginResponse;
import ru.startandroid.demoexam1.R;

public class LoginScreen extends AppCompatActivity {

    EditText email, password;
    Button goToSignUp, signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        email = findViewById(R.id.EmailSignIn);
        password = findViewById(R.id.PasswordSignIn);
        goToSignUp = findViewById(R.id.GoToSignUp);
        signIn = findViewById(R.id.SignIn);

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, RegisterScreen.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString()))
                {
                    ShowDialogWindow("Некоторые поля пустые!");
                }
                else{
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setEmail(email.getText().toString());
                    loginRequest.setPassword(password.getText().toString());
                    SignInUsers(loginRequest);
                }
            }
        });
    }

    public void SignInUsers(LoginRequest loginRequest){
        Call<LoginResponse> loginResponseCall = ApiClient.getService().LoginUser(loginRequest);

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful())
                {
                    LoginResponse loginResponse = response.body();
                    startActivity(new Intent(LoginScreen.this, MainActivity.class));
                }
                else{
                    Toast.makeText(LoginScreen.this, "Ошибка, повторите попытку позже...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                ShowDialogWindow(t.getLocalizedMessage());
            }
        });
    }

    public void ShowDialogWindow(String text){
        final AlertDialog alertDialog = new AlertDialog.Builder(LoginScreen.this)
                .setTitle("Ошибка").setMessage(text).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();
        alertDialog.show();
    }
}
