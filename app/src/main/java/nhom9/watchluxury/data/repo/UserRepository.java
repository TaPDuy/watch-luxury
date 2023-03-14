package nhom9.watchluxury.data.repo;

import android.util.Log;

import androidx.annotation.NonNull;

import nhom9.watchluxury.data.model.LoginInfo;
import nhom9.watchluxury.data.model.LoginResponse;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.remote.TokenManager;
import nhom9.watchluxury.data.remote.service.AuthService;
import nhom9.watchluxury.data.remote.service.UserService;
import nhom9.watchluxury.util.APIUtils;
import retrofit2.Call;
import retrofit2.Response;

public class UserRepository {

    private static final UserService USER_SERVICE = APIUtils.getUserService();
    private static final AuthService AUTH_SERVICE = APIUtils.getAuthenticationService();

    public void getUser(int id, Callback<User> callback) {

        USER_SERVICE.getUser(id, "Bearer " + TokenManager.getAccessToken()).enqueue(new retrofit2.Callback<User>() {

            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    Log.d("UserRepo", user.toString());
                    callback.onResponse(response.code(), user);
                } else {
                    Log.d("UserRepo", "Couldn't get user (" + response.code() + ")");
                    Log.d("UserRepo", call.toString());
                    callback.onResponse(response.code(), null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.d("UserRepo", "Couldn't get user");
                Log.d("UserRepo", call.toString());
                Log.d("UserRepo", t.getMessage());
                callback.onResponse(-1, null);
            }
        });
    }

    public void createUser(User user, Callback<User> callback) {

        AUTH_SERVICE.register(user).enqueue(new retrofit2.Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    Log.d("UserRepo", user.toString());
                    callback.onResponse(response.code(), user);
                } else {
                    Log.d("UserRepo", "Couldn't create new user (" + response.code() + ")");
                    Log.d("UserRepo", call.toString());
                    callback.onResponse(response.code(), null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.d("UserRepo", "Couldn't create new user");
                Log.d("UserRepo", call.toString());
                Log.d("UserRepo", t.getMessage());
                callback.onResponse(-1, null);
            }
        });
    }

    public void updateUser(int id, User user, Callback<User> callback) {

        USER_SERVICE.updateUser(id, user).enqueue(new retrofit2.Callback<User>() {

            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(response.code(), null);
                } else {
                    Log.d("UserRepo", "Couldn't update user (" + response.code() + ")");
                    Log.d("UserRepo", call.toString());
                    callback.onResponse(response.code(), null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.d("UserRepo", "Couldn't update user");
                Log.d("UserRepo", call.toString());
                Log.d("UserRepo", t.getMessage());
                callback.onResponse(-1, null);
            }
        });
    }

    public void authenticate(String username, String password, Callback<Integer> callback) {

        AUTH_SERVICE.login(new LoginInfo(username, password)).enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse data = response.body();
                    TokenManager.save(
                            data.getAccessToken(),
                            data.getRefreshToken(),
                            data.getLoggedInUserID()
                    );

                    Log.d("UserRepo", data.toString());
                    callback.onResponse(response.code(), data.getLoggedInUserID());
                } else if (response.code() >= 400 && response.code() < 500) {
                    Log.d("UserRepo", "Couldn't login (401)");
                    Log.d("UserRepo", call.toString());
                    Log.d("UserRepo", response.message());
                    callback.onResponse(response.code(), null);
                } else {
                    Log.d("UserRepo", "Couldn't login (" + response.code() + ")");
                    Log.d("UserRepo", call.toString());
                    callback.onResponse(response.code(), null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Log.d("UserRepo", "Couldn't authenticate");
                Log.d("UserRepo", call.toString());
                Log.d("UserRepo", t.getMessage());
                callback.onResponse(-1, null);
            }
        });
    }

    public interface Callback<T> {
        void onResponse(int responseCode, T response);
    }
}
