package nhom9.watchluxury.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import nhom9.watchluxury.R;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.databinding.ActivityEditUserBinding;
import nhom9.watchluxury.viewmodel.EditUserViewModel;

public class EditUserActivity extends AppCompatActivity {

    ActivityEditUserBinding binding;
    EditUserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = (User) getIntent().getSerializableExtra("user");
        viewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.Factory() {
                    @NonNull
                    @Override
                    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                        try {
                            return modelClass.getConstructor(User.class).newInstance(user);
                        } catch (IllegalAccessException | InstantiationException |
                                 InvocationTargetException | NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        ).get(EditUserViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_user);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        initObserver();

        binding.topBar.setNavigationOnClickListener(view -> finish());
    }

    private void initObserver() {
        viewModel.getStatus().observe(this, status -> {
            Intent intent = new Intent();
            switch (status) {
                case SUCCESS:
                    intent.putExtra("msg", "User updated");
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case ERROR:
                    Toast.makeText(EditUserActivity.this, "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
                    break;
                case NO_USER:
                    intent.putExtra("msg", "Invalid user data");
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                default:
                    break;
            }
        });

        Map<String, MutableLiveData<String>> errors = viewModel.getErrors();
        errors.get("email").observe(this, msg -> binding.tfUpdateEmail.setErrorEnabled(msg != null));
        errors.get("address").observe(this, msg -> binding.tfUpdateAddress.setErrorEnabled(msg != null));
        errors.get("phoneNumber").observe(this, msg -> binding.tfUpdatePhone.setErrorEnabled(msg != null));

        errors = viewModel.getPasswordErrors();
        errors.get("password1").observe(this, msg -> binding.tfUpdatePassword1.setErrorEnabled(msg != null));
        errors.get("password2").observe(this, msg -> binding.tfUpdatePassword2.setErrorEnabled(msg != null));
    }
}