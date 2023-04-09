package nhom9.watchluxury.data.remote.service;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.remote.model.FavoriteRequest;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FavoriteService {

    @GET("users/{id}/favorites/")
    Single<APIResource<List<Product>>> getUserFavorites(@Path("id") int id, @Header("Authorization") String accessToken);

    @GET("/favorites/")
    Single<APIResource<List<FavoriteRequest>>> getFavorites(@Query("user") int userID, @Query("product") int productID, @Header("Authorization") String accessToken);

    @POST("favorites/")
    Single<APIResource<FavoriteRequest>> addFavorite(@Body FavoriteRequest request, @Header("Authorization") String accessToken);

    @HTTP(method = "DELETE", path = "favorites/", hasBody = true)
    Single<APIResource<FavoriteRequest>> removeFavorite(@Body FavoriteRequest request, @Header("Authorization") String accessToken);
}
