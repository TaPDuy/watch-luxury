package nhom9.watchluxury;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

import nhom9.watchluxury.data.model.LoginInfo;
import nhom9.watchluxury.data.model.LoginResponse;
import nhom9.watchluxury.data.remote.TokenManager;
import nhom9.watchluxury.data.remote.service.AuthService;
import nhom9.watchluxury.databinding.ActivityLoginBinding;
import nhom9.watchluxury.util.APIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthService authService;


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authService = APIUtils.getAuthenticationService();
        TokenManager.init(getApplicationContext());

        binding.btnLogin.setOnClickListener(view -> login());
        binding.btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(Login.this, Register.class);
            startActivity(intent);
        });

        if (TokenManager.isAuthenticated()) {
            Intent intent = new Intent(Login.this , HomePage.class);
            startActivity(intent);
        }
    }



    private void login() {
        Context context = this;
        String account = binding.etLoginUsername.getText().toString();
        String pass = binding.etLoginPassword.getText().toString();

        if(TextUtils.isEmpty(account)){
            Toast.makeText(context, "Please! fill account ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(context, "Please! fill password to login", Toast.LENGTH_SHORT).show();
            return;
        }

        authService.login(new LoginInfo(account, pass)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse data = response.body();
                    TokenManager.save(
                            data.getAccessToken(),
                            data.getRefreshToken(),
                            data.getLoggedInUserID()
                    );

                    Toast.makeText(context, "Login successful!", Toast.LENGTH_LONG).show();
                    Log.d("LoginActivity", data.toString());
                    Intent intent = new Intent(Login.this , HomePage.class);
                    startActivity(intent);
                } else if (response.code() >= 400 && response.code() < 500) {
                    Toast.makeText(context, "Username or password is not correct", Toast.LENGTH_LONG).show();
                    Log.d("LoginActivity", "Couldn't login (401)");
                    Log.d("LoginActivity", call.toString());
                    Log.d("LoginActivity", response.message());
                } else {
                    Toast.makeText(context, "Oops, something went wrong!", Toast.LENGTH_LONG).show();
                    Log.d("LoginActivity", "Couldn't login (" + response.code() + ")");
                    Log.d("LoginActivity", call.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, "Oops, something went wrong!", Toast.LENGTH_LONG).show();
                Log.d("LoginActivity", "Couldn't login");
                Log.d("LoginActivity", call.toString());
                Log.d("LoginActivity", t.getMessage());
            }
        });
    }
}