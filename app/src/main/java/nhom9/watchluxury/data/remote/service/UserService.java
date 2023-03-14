package nhom9.watchluxury.data.remote.service;

import java.util.List;

import nhom9.watchluxury.data.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @GET("users/{id}")
    Call<User> getUser(@Path("id") int id, @Header("Authorization") String accessToken);

    @GET("users/")
    Call<List<User>> getUsers();

    @PUT("users/{id}/")
    Call<User> updateUser(@Path("id") int id, @Body User user);
}
