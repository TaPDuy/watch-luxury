package nhom9.watchluxury.data.repo;

import android.util.Log;

import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.DataSource;
import nhom9.watchluxury.data.local.source.OrderLocalSource;
import nhom9.watchluxury.data.model.Order;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.remote.source.OrderRemoteSource;

public class OrderRepository {

    private static final OrderRemoteSource API = DataSource.get(OrderRemoteSource.class);
    private static final OrderLocalSource DB = DataSource.get(OrderLocalSource.class);

    private static final String CLASS_NAME = "OrderRepo";

    public Single<APIResource<List<Order>>> getOrders(int userID) {
        return API.getOrders(userID)
                .doOnSuccess(
                        res -> {
                            List<Order> orders = res.getData();
                            for (Order order : orders)
                                DB.saveOrder(order).doOnComplete(
                                        () -> Log.d(CLASS_NAME, order.toString())
                                ).doOnError(
                                        throwable -> Log.e(CLASS_NAME, throwable.toString())
                                ).subscribe().dispose();
                        }
                )
                .doOnError(throwable -> Log.e(CLASS_NAME, Objects.requireNonNull(throwable.getMessage())));
    }

    public Single<APIResource<Order>> createOrder(int userID, Order order) {
        return API.createOrder(userID, order)
                .doOnSuccess(
                        res -> {
                                Log.d(CLASS_NAME, res.toString());
//                            db.insertProduct(prod).subscribe().dispose();
                        }
                )
                .doOnError(throwable -> Log.e(CLASS_NAME, Objects.requireNonNull(throwable.getMessage())));
    }
}
