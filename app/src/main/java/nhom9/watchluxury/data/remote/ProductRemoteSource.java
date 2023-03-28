package nhom9.watchluxury.data.remote;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.local.TokenManager;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.remote.service.ProductService;
import nhom9.watchluxury.util.APIUtils;

public class ProductRemoteSource {

    private static final ProductService PRODUCT_SERVICE = APIUtils.getProductService();

    public Single<APIResource<Product>> getProduct(int id) {
        return PRODUCT_SERVICE.getProduct(id, token());
    }

    private String token() {
        return "Bearer " + TokenManager.getAccessToken();
    }
}
