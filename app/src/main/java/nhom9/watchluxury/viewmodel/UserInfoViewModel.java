package nhom9.watchluxury.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.remote.TokenManager;
import nhom9.watchluxury.data.remote.service.UserService;
import nhom9.watchluxury.util.APIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoViewModel extends ViewModel {

    public enum Status {
        NONE,
        ERROR,
    }

    private final MutableLiveData<User> user;
    private final MutableLiveData<Status> status;
    private final UserService userService;

    public UserInfoViewModel() {
        userService = APIUtils.getUserService();
        loadUserInfo();

        user = new MutableLiveData<>(new User());
        status = new MutableLiveData<>(Status.NONE);
    }

    public MutableLiveData<User> getUser() {
        return this.user;
    }

    public MutableLiveData<Status> getStatus() {
        return this.status;
    }

    private void loadUserInfo() {

        if (!TokenManager.isAuthenticated())
            return;

        userService.getUser(
                TokenManager.getUserId(),
                "Bearer " + TokenManager.getAccessToken()
        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    user.setValue(response.body());
                    Log.d("HomeActivity", user.toString());
                } else {
                    status.setValue(Status.ERROR);
                    Log.d("HomeActivity", "Couldn't load user info (" + response.code() + ")");
                    Log.d("HomeActivity", call.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                status.setValue(Status.ERROR);
                Log.d("HomeActivity", "Couldn't load user info");
                Log.d("HomeActivity", call.toString());
                Log.d("HomeActivity", t.getMessage());
            }
        });
    }
}
