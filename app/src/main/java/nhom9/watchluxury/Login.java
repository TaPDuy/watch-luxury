package nhom9.watchluxury;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.Nullable;

import nhom9.watchluxury.data.model.LoginInfo;
import nhom9.watchluxury.data.model.LoginResponse;
import nhom9.watchluxury.util.APIUtils;
import nhom9.watchluxury.data.remote.service.AuthService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private EditText accountEdit , passEdit ;
    private Button btnlogin , btnregister ;
    private FirebaseAuth mAuth ;

    private AuthService authService;
    SharedPreferences sharedPref;


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        accountEdit = findViewById(R.id.inAccount);
        passEdit = findViewById(R.id.inPassLogin);

        authService = APIUtils.getAuthenticationService();
        sharedPref = this.getSharedPreferences("login_info", MODE_PRIVATE);

        btnlogin =  (Button) findViewById(R.id.btnLogin);
        btnregister = (Button)findViewById(R.id.btnRegister);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Login.this, Register.class);
                startActivity(intent);
            }
        });

        if (sharedPref.contains("userID")) {
            Intent intent = new Intent(Login.this , HomePage.class);
            startActivity(intent);
        }
    }



    private void login() {
        Context context = this;
        String account , pass ;
        account = accountEdit.getText().toString();
        pass = passEdit.getText().toString();
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
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("accessToken", data.getAccessToken());
                    editor.putString("refreshToken", data.getRefreshToken());
                    editor.putInt("userID", data.getLoggedInUserID());
                    editor.apply();

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

//        mAuth.signInWithEmailAndPassword(account, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(getApplicationContext(), "Welcome to login successfull", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Login.this , HomePage.class);
//                    startActivity(intent);
//                }else{
//                    Toast.makeText(getApplicationContext(), "Sorry! Login unsuccessful", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });
    }
}