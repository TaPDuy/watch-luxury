package nhom9.watchluxury.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import nhom9.watchluxury.R;
import nhom9.watchluxury.data.model.WeatherData;
import nhom9.watchluxury.databinding.ActivityWeatherBinding;
import nhom9.watchluxury.util.APIUtils;
import nhom9.watchluxury.viewmodel.WeatherViewModel;

public class WeatherActivity extends AppCompatActivity {

    String cityName;
    private Boolean result;

    private WeatherViewModel viewModel;
    private ActivityWeatherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        binding.frameLayoutProgressBar.setVisibility(View.VISIBLE);

        binding.btnSearch.setOnClickListener(view -> {
                binding.frameLayoutNoResult.setVisibility(View.GONE);
                binding.frameLayoutProgressBar.setVisibility(View.VISIBLE);

                viewModel.loadWeatherInfo();

                dismissKeyboard();
            }
        );

        viewModel.getImageUrl().observe(this, str -> APIUtils.loadImage(str, binding.ivWeatherCurrentIcon));

        viewModel.loadWeatherInfo();
    }

    public void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

}