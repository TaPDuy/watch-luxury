package nhom9.watchluxury.data.local;

import android.util.Log;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.local.dao.ProductDAO;
import nhom9.watchluxury.data.model.Product;

public class ProductLocalSource {

    private static final ProductDAO dao = AppDatabase.getInstance().productDAO();

    // For logging
    private static final String CLASS_NAME = "ProductDAO";

    public Single<Product> getProduct(int id) {
        return dao.get(id)
                .doOnSuccess(product -> Log.d(CLASS_NAME, "Query: " + product))
                .doOnError(throwable -> Log.e(CLASS_NAME, throwable.getMessage()));
    }

    public Completable insertProduct(Product product) {
        return dao.insert(product)
                .doOnComplete(() -> Log.d(CLASS_NAME, "Insert: " + product));
    }
}
