package nhom9.watchluxury.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import nhom9.watchluxury.data.remote.TokenManager;
import nhom9.watchluxury.databinding.ActivityHomePageBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogout.setOnClickListener(view -> {
            TokenManager.deleteTokens();
            finish();
        });

        binding.btnUserInfo.setOnClickListener(view -> {
            Intent i = new Intent(this, UserInfoActivity.class);
            startActivity(i);
        });
    }


}