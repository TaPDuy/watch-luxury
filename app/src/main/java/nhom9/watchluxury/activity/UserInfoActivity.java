package nhom9.watchluxury.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import nhom9.watchluxury.R;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.databinding.ActivityUserInfoBinding;
import nhom9.watchluxury.viewmodel.UserInfoViewModel;

public class UserInfoActivity extends AppCompatActivity {

    ActivityUserInfoBinding binding;
    UserInfoViewModel viewModel;

    ActivityResultLauncher<User> editUserLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        initObserver();
        initLauncher();

        binding.btnEdit.setOnClickListener(view -> editUserLauncher.launch(viewModel.getUser().getValue()));
    }

    private void initObserver() {
        viewModel.getStatus().observe(this, status -> {
            switch (status) {
                case SUCCESS:
                    binding.btnEdit.setClickable(true);
                    break;
                case ERROR:
                    Toast.makeText(UserInfoActivity.this, "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
                    binding.btnEdit.setClickable(false);
                    break;
                default:
                    break;
            }
        });
    }

    private void initLauncher() {
        editUserLauncher = registerForActivityResult(
                new ActivityResultContract<User, String>() {
                    @Override
                    public String parseResult(int resultCode, @Nullable Intent intent) {
                        if (intent != null)
                            return intent.getStringExtra("msg");
                        return null;
                    }

                    @NonNull
                    @Override
                    public Intent createIntent(@NonNull Context context, User user) {
                        Intent intent = new Intent(context, EditUserActivity.class);
                        intent.putExtra("user", user);
                        return intent;
                    }
                },
                result -> {
                    if (result != null)
                        Toast.makeText(UserInfoActivity.this, result, Toast.LENGTH_SHORT).show();
                    viewModel.onReload();
                }
        );
    }
}