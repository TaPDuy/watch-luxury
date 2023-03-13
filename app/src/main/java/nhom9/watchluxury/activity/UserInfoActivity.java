package nhom9.watchluxury.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import nhom9.watchluxury.R;
import nhom9.watchluxury.databinding.ActivityUserInfoBinding;
import nhom9.watchluxury.viewmodel.UserInfoViewModel;

public class UserInfoActivity extends AppCompatActivity {

    ActivityUserInfoBinding binding;
    UserInfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        initObserver();
    }

    public void initObserver() {
        viewModel.getStatus().observe(this, status -> {
            switch (status) {
                case ERROR:
                    Toast.makeText(UserInfoActivity.this, "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        });
    }
}