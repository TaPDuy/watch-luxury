package nhom9.watchluxury.data.remote.service;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.model.Category;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.model.Product;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {

    @GET("/products/{id}/")
    Single<APIResource<Product>> getProduct(@Path("id") int id, @Header("Authorization") String accessToken);

    @GET("/products/categories/")
    Single<APIResource<List<Category>>> getCategories(@Header("Authorization") String accessToken);

    @GET("/products/")
    Single<APIResource<List<Product>>> getProductsByCategory(@Query("category") String categorySlug);

    @GET("/products/")
    Single<APIResource<List<Product>>> getProductByKeyword(@Query("search") String keyword);
}
