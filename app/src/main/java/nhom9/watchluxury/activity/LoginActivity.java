package nhom9.watchluxury.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.Nullable;

import nhom9.watchluxury.R;
import nhom9.watchluxury.data.local.AppDatabase;
import nhom9.watchluxury.data.remote.TokenManager;
import nhom9.watchluxury.databinding.ActivityLoginBinding;
import nhom9.watchluxury.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TokenManager.init(getApplicationContext());
        AppDatabase.init(getApplicationContext());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        initObserver();


        binding.btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        if (TokenManager.isAuthenticated()) {
            Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
            startActivity(intent);
        }
    }

    private void initObserver() {
        viewModel.getStatus().observe(this, status -> {
            switch (status) {
                case SUCCESS:
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
                    startActivity(intent);
                    break;
                case WRONG_LOGIN:
                    Toast.makeText(LoginActivity.this, "Username or password is not correct", Toast.LENGTH_SHORT).show();
                    break;
                case USERNAME_EMPTY:
                    Toast.makeText(LoginActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
                    break;
                case PASSWORD_EMPTY:
                    Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    Toast.makeText(LoginActivity.this, "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        });
    }
}