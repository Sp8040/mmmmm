package ru.startandroid.demoexam1.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.startandroid.demoexam1.Api.ApiClient;
import ru.startandroid.demoexam1.R;
import ru.startandroid.demoexam1.RegisterFiles.RegisterRequest;
import ru.startandroid.demoexam1.RegisterFiles.RegisterResponse;

public class RegisterScreen extends AppCompatActivity {
    EditText email, password, firstName, lastName, repeatPassword;
    Button goToSignIn, signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        email = findViewById(R.id.EmailSignUp);
        password = findViewById(R.id.PasswordSignUp);
        firstName = findViewById(R.id.FirstName);
        lastName = findViewById(R.id.LastName);
        repeatPassword = findViewById(R.id.RepeatPassword);
        goToSignIn = findViewById(R.id.GoToSignIn);
        signUp = findViewById(R.id.SignUp);

        goToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterScreen.this, LoginScreen.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(firstName.getText().toString()) || TextUtils.isEmpty(lastName.getText().toString()))
                {
                    ShowDialogWindow("Некоторые поля пустые!");
                }
                else
                {
                    if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                        if(password.getText().toString().equals(repeatPassword.getText().toString()))
                        {
                            RegisterRequest registerRequest = new RegisterRequest();
                            registerRequest.setEmail(email.getText().toString());
                            registerRequest.setPassword(password.getText().toString());
                            registerRequest.setFirstName(firstName.getText().toString());
                            registerRequest.setLastName(lastName.getText().toString());

                            SignUpUsers(registerRequest);
                        }
                        else{
                            ShowDialogWindow("Пароли не совпадают");
                        }
                    }
                    else {
                        ShowDialogWindow("Неправильный формат почты");
                    }
                }
            }
        });
    }

    public void SignUpUsers(RegisterRequest registerRequest)
    {
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().RegisterUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(RegisterScreen.this, "Вы успешно зарегистрировались", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterScreen.this, LoginScreen.class));
                }
                else{
                    ShowDialogWindow("Ошибка, повторите попытку позже...");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterScreen.this, "Вы успешно зарегистрировались", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterScreen.this, LoginScreen.class));
            }
        });
    }

    public void ShowDialogWindow(String text){
        final AlertDialog alertDialog = new AlertDialog.Builder(RegisterScreen.this).setTitle("Error").setMessage(text).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();

        alertDialog.show();
    }
}
