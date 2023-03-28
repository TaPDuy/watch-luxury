package nhom9.watchluxury.data.remote.service;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.model.Product;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ProductService {

    @GET("/products/{id}/")
    Single<APIResource<Product>> getProduct(@Path("id") int id, @Header("Authorization") String accessToken);
}
