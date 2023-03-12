package nhom9.watchluxury.viewmodel;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import nhom9.watchluxury.data.model.LoginInfo;
import nhom9.watchluxury.data.model.LoginResponse;
import nhom9.watchluxury.data.remote.TokenManager;
import nhom9.watchluxury.data.remote.service.AuthService;
import nhom9.watchluxury.util.APIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    public enum Status {
        NONE,
        SUCCESS,
        USERNAME_EMPTY,
        PASSWORD_EMPTY,
        ERROR,
        WRONG_LOGIN,
    }

    private final MutableLiveData<String> username;
    private final MutableLiveData<String> password;
    private final MutableLiveData<Status> status;
    private final AuthService authService;

    public LoginViewModel() {
        authService = APIUtils.getAuthenticationService();
        this.username = new MutableLiveData<>("");
        this.password = new MutableLiveData<>("");
        this.status = new MutableLiveData<>(Status.NONE);
    }

    public MutableLiveData<Status> getStatus() {
        return this.status;
    }

    public MutableLiveData<String> getUsername() {
        return this.username;
    }

    public MutableLiveData<String> getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public void onLoginClicked() {
        String user = username.getValue();
        String pass = password.getValue();

        if(TextUtils.isEmpty(user)){
            status.setValue(Status.USERNAME_EMPTY);
            return;
        }
        if(TextUtils.isEmpty(pass)){
            status.setValue(Status.PASSWORD_EMPTY);
            return;
        }

        authService.login(new LoginInfo(user, pass)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse data = response.body();
                    TokenManager.save(
                            data.getAccessToken(),
                            data.getRefreshToken(),
                            data.getLoggedInUserID()
                    );

                    status.setValue(Status.SUCCESS);
                    Log.d("LoginActivity", data.toString());
                } else if (response.code() >= 400 && response.code() < 500) {
                    status.setValue(Status.WRONG_LOGIN);
                    Log.d("LoginActivity", "Couldn't login (401)");
                    Log.d("LoginActivity", call.toString());
                    Log.d("LoginActivity", response.message());
                } else {
                    status.setValue(Status.ERROR);
                    Log.d("LoginActivity", "Couldn't login (" + response.code() + ")");
                    Log.d("LoginActivity", call.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                status.setValue(Status.ERROR);
                Log.d("LoginActivity", "Couldn't login");
                Log.d("LoginActivity", call.toString());
                Log.d("LoginActivity", t.getMessage());
            }
        });
    }
}
