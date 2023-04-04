package nhom9.watchluxury.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import nhom9.watchluxury.R;
import nhom9.watchluxury.databinding.ActivitySettingsBinding;
import nhom9.watchluxury.util.SettingsUtils;
import nhom9.watchluxury.viewmodel.UserInfoViewModel;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

//        viewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
//        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        binding.topBar.setNavigationOnClickListener(view -> finish());

        binding.swDarkMode.setChecked(SettingsUtils.isDarkMode());
        binding.swDarkMode.setOnCheckedChangeListener((view, isChecked) -> {
            SettingsUtils.setDarkMode(isChecked);
        });
    }
}