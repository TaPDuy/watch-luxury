package nhom9.watchluxury;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.remote.service.AuthService;
import nhom9.watchluxury.databinding.ActivityRegisterBinding;
import nhom9.watchluxury.util.APIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
//    private EditText emailEdit , passEdit ;
//    nameEdit , rePassEdit ;
//    private Button btnRegister;
//    private FirebaseAuth mAuth;

    private ActivityRegisterBinding binding;
    private AuthService authService;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        mAuth = FirebaseAuth.getInstance();
        authService = APIUtils.getAuthenticationService();

        binding.btnRegister.setOnClickListener(view -> register());
    }
    private void register(){

        Context context = binding.getRoot().getContext();
        String username = binding.etUsername.getText().toString();
        String email = binding.etEmail.getText().toString();
        String address = binding.etAddress.getText().toString();
        String password1 = binding.etPassword1.getText().toString();
        String password2 = binding.etPassword2.getText().toString();

        List<String> inputs = new ArrayList<>(Arrays.asList(
                username, email, address, password1, password2
        ));

        if (inputs.stream().anyMatch(TextUtils::isEmpty)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password1.equals(password2)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        authService.register(
                new User.Builder()
                        .username(username)
                        .email(email)
                        .address(address)
                        .password(password1)
                        .build()
        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User data = response.body();
                    Toast.makeText(context, "Registration successful!", Toast.LENGTH_LONG).show();
                    Log.d("RegisterActivity", data.toString());
                    finish();
                } else {
                    Toast.makeText(context, "Oops, something went wrong!", Toast.LENGTH_LONG).show();
                    Log.d("RegisterActivity", "Couldn't register (" + response.code() + ")");
                    Log.d("RegisterActivity", call.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toast.makeText(context, "Oops, something went wrong!", Toast.LENGTH_LONG).show();
                Log.d("LoginActivity", "Couldn't register");
                Log.d("LoginActivity", call.toString());
                Log.d("LoginActivity", t.getMessage());
            }
        });

//        String email , pass ;
//        email = emailEdit.getText().toString();
//        pass = passEdit.getText().toString();
//        if(TextUtils.isEmpty(email)){
//            Toast.makeText(this, "Please! fill email ", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(TextUtils.isEmpty(pass)){
//            Toast.makeText(this, "Please! fill password ", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(getApplicationContext(), "Create account successfull",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Register.this, Login.class);
//                    startActivity(intent);
//
//                }else{
//                    Toast.makeText(Register.this, "Not success to create an account", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}