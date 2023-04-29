package nhom9.watchluxury.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import nhom9.watchluxury.R;
import nhom9.watchluxury.data.local.AppDatabase;
import nhom9.watchluxury.data.local.TokenManager;
import nhom9.watchluxury.databinding.ActivityMainBinding;
import nhom9.watchluxury.util.SettingsUtils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
//        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        TokenManager.init(getApplicationContext());
        AppDatabase.init(getApplicationContext());
        SettingsUtils.init(getApplicationContext());

        if (TokenManager.isAuthenticated()) {
            Intent intent = new Intent(this , HomeActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this , LoginActivity.class);
            startActivity(intent);
        }
    }
}