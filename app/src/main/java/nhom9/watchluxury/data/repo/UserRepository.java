package nhom9.watchluxury.data.repo;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import nhom9.watchluxury.data.model.LoginCredentials;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.model.api.APIResponse;
import nhom9.watchluxury.data.model.api.LoginRequest;
import nhom9.watchluxury.data.model.api.RegisterRequest;
import nhom9.watchluxury.data.model.api.ResponseCode;
import nhom9.watchluxury.data.remote.TokenManager;
import nhom9.watchluxury.data.remote.service.AuthService;
import nhom9.watchluxury.data.remote.service.UserService;
import nhom9.watchluxury.util.APIUtils;
import retrofit2.Call;
import retrofit2.Response;

public class UserRepository {

    private static final UserService USER_SERVICE = APIUtils.getUserService();
    private static final AuthService AUTH_SERVICE = APIUtils.getAuthenticationService();
    private static final String FAIL_MSG = "Request failed";

    public void getUser(int id, Callback<User> callback) {

        USER_SERVICE.getUser(id, "Bearer " + TokenManager.getAccessToken()).enqueue(new retrofit2.Callback<APIResponse<User>>() {

            @Override
            public void onResponse(@NonNull Call<APIResponse<User>> call, @NonNull Response<APIResponse<User>> responsePackage) {

                APIResponse<User> response = handleResponse(responsePackage);
                User user = response.getData();

                if (responsePackage.isSuccessful())
                    Log.d("UserRepo", "Retrieved user: \n" + user.toString());
                else {
                    Log.d("UserRepo", "Couldn't get user (" + response.getResponseCode() + ")");
                    Log.d("UserRepo", response.getMessage());
                    Log.d("UserRepo", call.toString());
                }

                callback.onResponse(response.getResponseCode(), user, response.getMessage());
            }

            @Override
            public void onFailure(@NonNull Call<APIResponse<User>> call, @NonNull Throwable t) {
                Log.d("UserRepo", "Couldn't get user");
                Log.d("UserRepo", call.toString());
                Log.d("UserRepo", t.getMessage());
                callback.onResponse(ResponseCode.FAILURE, null, FAIL_MSG);
            }
        });
    }

    public void createUser(RegisterRequest request, Callback<User> callback) {

        AUTH_SERVICE.register(request).enqueue(new retrofit2.Callback<APIResponse<User>>() {
            @Override
            public void onResponse(@NonNull Call<APIResponse<User>> call, @NonNull Response<APIResponse<User>> responsePackage) {

                APIResponse<User> response = handleResponse(responsePackage);
                User user = response.getData();

                if (response.getResponseCode() == ResponseCode.SUCCESS)
                    Log.d("UserRepo", "Updated user: \n" + user.toString());
                else {
                    Log.d("UserRepo", "Couldn't create new user (" + response.getResponseCode() + ")");
                    Log.d("UserRepo", response.getMessage());
                    Log.d("UserRepo", call.toString());
                }

                callback.onResponse(response.getResponseCode(), user, response.getMessage());
            }

            @Override
            public void onFailure(@NonNull Call<APIResponse<User>> call, @NonNull Throwable t) {

                Log.d("UserRepo", "Couldn't create new user");
                Log.d("UserRepo", call.toString());
                Log.d("UserRepo", t.getMessage());
                callback.onResponse(ResponseCode.FAILURE, null, FAIL_MSG);
            }
        });
    }

    public void updateUser(int id, User user, Callback<User> callback) {

        USER_SERVICE.updateUser(id, user, "Bearer " + TokenManager.getAccessToken()).enqueue(new retrofit2.Callback<APIResponse<User>>() {

            @Override
            public void onResponse(@NonNull Call<APIResponse<User>> call, @NonNull Response<APIResponse<User>> responsePackage) {

                APIResponse<User> response = handleResponse(responsePackage);

                if (response.getResponseCode() != ResponseCode.SUCCESS) {
                    Log.d("UserRepo", "Couldn't update user (" + response.getResponseCode() + ")");
                    Log.d("UserRepo", response.getMessage());
                    Log.d("UserRepo", call.toString());
                }

                callback.onResponse(response.getResponseCode(), response.getData(), response.getMessage());
            }

            @Override
            public void onFailure(@NonNull Call<APIResponse<User>> call, @NonNull Throwable t) {
                Log.d("UserRepo", "Couldn't update user");
                Log.d("UserRepo", call.toString());
                Log.d("UserRepo", t.getMessage());
                callback.onResponse(ResponseCode.FAILURE, null, FAIL_MSG);
            }
        });
    }

    public void authenticate(String username, String password, Callback<Integer> callback) {

        AUTH_SERVICE.login(new LoginRequest(username, password)).enqueue(new retrofit2.Callback<APIResponse<LoginCredentials>>() {
            @Override
            public void onResponse(@NonNull Call<APIResponse<LoginCredentials>> call, @NonNull Response<APIResponse<LoginCredentials>> responsePackage) {

                APIResponse<LoginCredentials> response = handleResponse(responsePackage);
                LoginCredentials data = response.getData();

                if (response.getResponseCode() == ResponseCode.SUCCESS) {

                    TokenManager.save(
                            data.getAccessToken(),
                            data.getRefreshToken(),
                            data.getLoggedInUserID()
                    );

                    Log.d("UserRepo", "New credentials saved: " + data);
                    callback.onResponse(response.getResponseCode(), data.getLoggedInUserID(), response.getMessage());
                } else {

                    Log.d("UserRepo", "Couldn't login (" + response.getResponseCode() + ")");
                    Log.d("UserRepo", response.getMessage());
                    Log.d("UserRepo", call.toString());

                    if (responsePackage.code() >= 400 && responsePackage.code() < 500)
                        Log.d("UserRepo", responsePackage.message());

                    callback.onResponse(response.getResponseCode(), null, response.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResponse<LoginCredentials>> call, @NonNull Throwable t) {
                Log.d("UserRepo", "Couldn't authenticate");
                Log.d("UserRepo", call.toString());
                Log.d("UserRepo", t.getMessage());
                callback.onResponse(ResponseCode.FAILURE, null, FAIL_MSG);
            }
        });
    }

    public interface Callback<T> {
        void onResponse(int responseCode, T data, String msg);
    }

    private <T> APIResponse<T> handleResponse(Response<APIResponse<T>> responsePackage) {

        if (responsePackage.isSuccessful()) {
            APIResponse<T> response = responsePackage.body();
            assert response != null;
            return response;
        }

        Log.d("UserRepo", "Request failed (" + responsePackage.code() + ")");

        // Deserializing response
        int code = ResponseCode.FAILURE;
        String msg = FAIL_MSG;
        try {

            assert responsePackage.errorBody() != null;
            JSONObject error = new JSONObject(responsePackage.errorBody().string());
            msg = error.getString("msg");
            code = error.getInt("code");
        } catch (JSONException | IOException e) {
            Log.d("UserRepo", e.getMessage());
        }

        return new APIResponse<>(code, msg, null);
    }
}
