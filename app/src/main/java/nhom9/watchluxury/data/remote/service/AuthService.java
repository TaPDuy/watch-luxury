package nhom9.watchluxury.data.remote.service;

import io.reactivex.rxjava3.core.Single;
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
    Single<APIResponse<LoginCredentials>> login(@Body LoginRequest info);

    @POST("register/")
    Single<APIResponse<User>> register(@Body RegisterRequest request);
}
