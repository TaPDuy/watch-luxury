package nhom9.watchluxury.data.repo;

import android.util.Log;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.local.ProductLocalSource;
import nhom9.watchluxury.data.model.Category;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.data.remote.ProductRemoteSource;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.remote.model.ResponseCode;
import nhom9.watchluxury.util.DataSource;

public class ProductRepository {

    private static final ProductRemoteSource api = DataSource.getRemoteProduct();
    private static final ProductLocalSource db = DataSource.getLocalProduct();

    // For logging
    private static final String CLASS_NAME = "ProductRepo";

    public Flowable<APIResource<Product>> getProduct(int id) {

        Single<APIResource<Product>> localData = db.getProduct(id)
                .map(product -> new APIResource<>(ResponseCode.SUCCESS, "Local data", product));

        Single<APIResource<Product>> remoteData = api.getProduct(id)
                .doOnSuccess(
                        res -> {
                            Product prod = res.getData();
                            db.insertProduct(prod).subscribe().dispose();
                        }
                )
                .doOnError(throwable -> Log.e(CLASS_NAME, throwable.getMessage()));

        return Single.concatArrayDelayError(localData, remoteData);
    }

    public Single<APIResource<List<Category>>> getCategories() {
        return api.getCategories()
                .doOnSuccess(
                        res -> {
                            List<Category> cats = res.getData();
                            for (Category cat : cats)
                                Log.d(CLASS_NAME, cat.toString());
//                            db.insertProduct(prod).subscribe().dispose();
                        }
                )
                .doOnError(throwable -> Log.e(CLASS_NAME, throwable.getMessage()));
    }

    public Single<APIResource<List<Product>>> getProductByCategory(Category category) {
        return api.getProductByCategory(category)
                .doOnSuccess(
                        res -> {
                            List<Product> prod = res.getData();
                            for (Product cat : prod)
                                Log.d(CLASS_NAME, cat.toString());
//                            db.insertProduct(prod).subscribe().dispose();
                        }
                )
                .doOnError(throwable -> Log.e(CLASS_NAME, throwable.getMessage()));
    }

    public Single<APIResource<List<Product>>> searchProducts(String keyword) {
        return api.getProductByKeyword(keyword)
                .doOnSuccess(
                        res -> {
                            List<Product> prod = res.getData();
                            for (Product cat : prod)
                                Log.d(CLASS_NAME, cat.toString());
//                            db.insertProduct(prod).subscribe().dispose();
                        }
                )
                .doOnError(throwable -> Log.e(CLASS_NAME, throwable.getMessage()));
    }
}
