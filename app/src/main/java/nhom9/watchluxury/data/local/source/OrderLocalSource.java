package nhom9.watchluxury.data.local.source;

import android.util.Log;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import nhom9.watchluxury.data.local.AppDatabase;
import nhom9.watchluxury.data.local.dao.OrderDAO;
import nhom9.watchluxury.data.local.model.OrderItem;
import nhom9.watchluxury.data.model.Order;
import nhom9.watchluxury.data.model.Product;

public class OrderLocalSource extends LocalSource {

    private static final OrderDAO DAO = AppDatabase.getInstance().orderDAO();

    private static final String CLASS_NAME = "OrderDAO";

    public Completable saveOrder(Order order) {
        return DAO.insert(order.asTableRow()).andThen(
                Completable.fromAction(() -> {
                    order.getProducts().forEach(product -> {
                        DAO.insert(new OrderItem(order.getId(), product.getId()))
                                .subscribe().dispose();
                    });
                })
        );
    }
}
