package nhom9.watchluxury.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import nhom9.watchluxury.R;
import nhom9.watchluxury.activity.fragment.LoadingFragment;
import nhom9.watchluxury.activity.fragment.LoginFragment;
import nhom9.watchluxury.data.local.AppDatabase;
import nhom9.watchluxury.data.local.TokenManager;
import nhom9.watchluxury.databinding.ActivityMainBinding;
import nhom9.watchluxury.util.SettingsUtils;
import nhom9.watchluxury.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    private static boolean init = false;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!init) {
            Log.i("Init", "Initializing...");
            TokenManager.init(getApplicationContext());
            AppDatabase.init(getApplicationContext());
            SettingsUtils.init(getApplicationContext());
            init = true;
            Log.i("Init", "Initialized");
        }

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        fragmentManager = getSupportFragmentManager();

        initObserver();

        if (TokenManager.isAuthenticated()) {
            loadData();
        } else {
            requireLogin();
        }
    }

    private void initObserver() {
        viewModel.getLoading().observe(this, finish -> {
            if (finish) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        Log.i("Init", "Loading data...");
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_loginContainer, new LoadingFragment());
        transaction.commit();

        viewModel.loadData();
    }

    private void requireLogin() {
        Log.i("Init", "Waiting for login...");
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_loginContainer, new LoginFragment());
        transaction.commit();
    }

    @Override
    public void onLoginResult(boolean res) {
        if (res) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fl_loginContainer, new LoadingFragment());
            transaction.commit();

            Log.i("Init", "Loading data...");
            viewModel.loadData();
        }
    }
}