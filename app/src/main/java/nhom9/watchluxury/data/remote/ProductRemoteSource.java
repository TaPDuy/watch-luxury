package nhom9.watchluxury.data.remote;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.local.TokenManager;
import nhom9.watchluxury.data.model.Category;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.remote.model.FavoriteRequest;
import nhom9.watchluxury.data.remote.service.FavoriteService;
import nhom9.watchluxury.data.remote.service.ProductService;
import nhom9.watchluxury.util.APIUtils;

public class ProductRemoteSource {

    private static final ProductService PRODUCT_SERVICE = APIUtils.getProductService();
    private static final FavoriteService FAVORITE_SERVICE = APIUtils.getFavoriteService();

    public Single<APIResource<Product>> getProduct(int id) {
        return PRODUCT_SERVICE.getProduct(id, token());
    }

    public Single<APIResource<List<Category>>> getCategories() {
        return PRODUCT_SERVICE.getCategories(token());
    }

    public Single<APIResource<List<Product>>> getProductByCategory(Category category) {
        return PRODUCT_SERVICE.getProductsByCategory(category.getSlug());
    }

    public Single<APIResource<List<Product>>> getProductByKeyword(String keyword) {
        return PRODUCT_SERVICE.getProductByKeyword(keyword);
    }

    public Single<APIResource<FavoriteRequest>> addFavorite(int userID, int productID) {
        return FAVORITE_SERVICE.addFavorite(new FavoriteRequest(userID, productID), token());
    }

    public Single<APIResource<FavoriteRequest>> removeFavorite(int userID, int productID) {
        return FAVORITE_SERVICE.removeFavorite(new FavoriteRequest(userID, productID), token());
    }

    public Single<APIResource<List<FavoriteRequest>>> getFavorite(int userID, int productID) {
        return FAVORITE_SERVICE.getFavorites(userID, productID, token());
    }

    private String token() {
        return "Bearer " + TokenManager.getAccessToken();
    }

}
