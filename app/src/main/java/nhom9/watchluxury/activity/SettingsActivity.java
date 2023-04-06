package nhom9.watchluxury.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import nhom9.watchluxury.R;
import nhom9.watchluxury.databinding.ActivitySettingsBinding;
import nhom9.watchluxury.util.SettingsUtils;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        binding.topBar.setNavigationOnClickListener(view -> finish());

        binding.swDarkMode.setChecked(SettingsUtils.isDarkMode());
        binding.swDarkMode.setOnCheckedChangeListener(
                (view, isChecked) -> SettingsUtils.setDarkMode(isChecked)
        );
    }
}