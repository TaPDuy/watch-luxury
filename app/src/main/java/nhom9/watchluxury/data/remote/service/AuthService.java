package nhom9.watchluxury.data.remote.service;

import nhom9.watchluxury.data.model.LoginInfo;
import nhom9.watchluxury.data.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("login/")
    Call<LoginResponse> login(@Body LoginInfo info);
}
