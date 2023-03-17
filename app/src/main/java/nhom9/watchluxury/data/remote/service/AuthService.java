package nhom9.watchluxury.data.remote.service;

import nhom9.watchluxury.data.model.api.LoginRequest;
import nhom9.watchluxury.data.model.LoginCredentials;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.model.api.APIResponse;
import nhom9.watchluxury.data.model.api.RegisterRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService extends Service {

    @POST("login/")
    Call<APIResponse<LoginCredentials>> login(@Body LoginRequest info);

    @POST("register/")
    Call<APIResponse<User>> register(@Body RegisterRequest request);

    @POST("login/refresh/")
    Call<APIResponse<LoginCredentials>> refresh(@Body String refreshToken);
}
