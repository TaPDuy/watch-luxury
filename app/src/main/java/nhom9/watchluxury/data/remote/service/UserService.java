package nhom9.watchluxury.data.remote.service;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.model.api.APIResponse;
import nhom9.watchluxury.data.model.api.ChangePasswordRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @GET("users/{id}")
    Single<APIResponse<User>> getUser(@Path("id") int id, @Header("Authorization") String accessToken);

    @PUT("users/{id}/")
    Call<APIResponse<User>> updateUser(@Path("id") int id, @Body User user, @Header("Authorization") String accessToken);

    @PUT("users/{id}/change_password/")
    Call<APIResponse<Object>> changePassword(@Path("id") int id, @Body ChangePasswordRequest request, @Header("Authorization") String accessToken);
}
