package nhom9.watchluxury;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import nhom9.watchluxury.databinding.ActivityHomePageBinding;

public class HomePage extends AppCompatActivity {

    private ActivityHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogout.setOnClickListener(view -> {
            SharedPreferences.Editor editor = view.getContext().getSharedPreferences("login_info", MODE_PRIVATE).edit();
            editor.remove("accessToken");
            editor.remove("refreshToken");
            editor.remove("userID");
            editor.apply();
            finish();
        });
    }
}