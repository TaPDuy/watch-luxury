package nhom9.watchluxury.data.remote.service;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.model.APIResource;
import nhom9.watchluxury.data.remote.model.ChangePasswordRequest;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @GET("users/{id}")
    Single<APIResource<User>> getUser(@Path("id") int id, @Header("Authorization") String accessToken);

    @PUT("users/{id}/")
    Single<APIResource<User>> updateUser(@Path("id") int id, @Body User user, @Header("Authorization") String accessToken);

    @PUT("users/{id}/change_password/")
    Single<APIResource<Object>> changePassword(@Path("id") int id, @Body ChangePasswordRequest request, @Header("Authorization") String accessToken);
}
