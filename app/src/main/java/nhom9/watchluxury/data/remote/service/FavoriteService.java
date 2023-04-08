package nhom9.watchluxury.data.remote.service;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.remote.model.FavoriteRequest;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FavoriteService {

    @GET("users/{id}/favorites/")
    Single<APIResource<List<Product>>> getFavorites(@Path("id") int id, @Header("Authorization") String accessToken);

    @POST("favorite/")
    Single<APIResource<FavoriteRequest>> addFavorite(@Body FavoriteRequest request, @Header("Authorization") String accessToken);

    @DELETE("favorite/")
    Single<APIResource<FavoriteRequest>> removeFavorite(@Body FavoriteRequest request, @Header("Authorization") String accessToken);
}
