package nhom9.watchluxury.data.remote;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.local.TokenManager;
import nhom9.watchluxury.data.model.Order;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.remote.model.CreateOrderRequest;
import nhom9.watchluxury.data.remote.service.OrderService;
import nhom9.watchluxury.util.APIUtils;

public class OrderRemoteSource {

    private static final OrderService ORDER_SERVICE = APIUtils.getOrderService();

    public Single<APIResource<List<Order>>> getOrders(int userID) {
        return ORDER_SERVICE.getOrdersByUser(userID);
    }

    public Single<APIResource<Order>> createOrder(int userID, Order order) {

        int[] productIDs = order.getProducts().stream().mapToInt(Product::getId).toArray();
        return ORDER_SERVICE.createOrder(new CreateOrderRequest(
                userID,
                order.getName(),
                order.getPhoneNumber(),
                order.getAddress(),
                productIDs
        ), token());
    }

    private String token() {
        return "Bearer " + TokenManager.getAccessToken();
    }
}
