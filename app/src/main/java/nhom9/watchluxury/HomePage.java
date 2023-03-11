package nhom9.watchluxury;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.remote.TokenManager;
import nhom9.watchluxury.data.remote.service.UserService;
import nhom9.watchluxury.databinding.ActivityHomePageBinding;
import nhom9.watchluxury.util.APIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage extends AppCompatActivity {

    private ActivityHomePageBinding binding;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userService = APIUtils.getUserService();

        binding.btnLogout.setOnClickListener(view -> {
            TokenManager.deleteTokens();
            finish();
        });

        loadUserInfo();
    }

    private void loadUserInfo() {
        Context context = binding.getRoot().getContext();

        int userID = TokenManager.getUserId();

        if (!TokenManager.isAuthenticated()) {
            Toast.makeText(context, "Oops, something went wrong!", Toast.LENGTH_LONG).show();
            Log.d("HomeActivity", "User didn't login correctly");
            finish();
        }

        String accessToken = TokenManager.getAccessToken();
        userService.getUser(userID, "Bearer " + accessToken).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User data = response.body();
                    Log.d("HomeActivity", data.toString());
                    binding.tvUserInfo.setText(data.toString());
                } else {
                    Toast.makeText(context, "Oops, something went wrong!", Toast.LENGTH_LONG).show();
                    Log.d("HomeActivity", "Couldn't load user info (" + response.code() + ")");
                    Log.d("HomeActivity", call.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toast.makeText(context, "Oops, something went wrong!", Toast.LENGTH_LONG).show();
                Log.d("HomeActivity", "Couldn't load user info");
                Log.d("HomeActivity", call.toString());
                Log.d("HomeActivity", t.getMessage());
            }
        });
    }
}