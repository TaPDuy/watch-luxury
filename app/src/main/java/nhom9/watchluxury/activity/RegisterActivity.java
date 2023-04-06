package nhom9.watchluxury.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import nhom9.watchluxury.R;
import nhom9.watchluxury.databinding.ActivityRegisterBinding;
import nhom9.watchluxury.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    RegisterViewModel viewModel;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        initObserver();
    }

    private void initObserver() {
        viewModel.getStatus().observe(this, status -> {
            switch (status) {
                case SUCCESS:
                    Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case EMPTY_FIELDS:
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    break;
                case UNMATCHED_PASSWORD:
                    Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    Toast.makeText(RegisterActivity.this, "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        });
    }
}