package nhom9.watchluxury.data.remote.service;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.model.Order;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.remote.model.CreateOrderRequest;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderService {

    @GET("/orders/")
    Single<APIResource<List<Order>>> getOrdersByUser(@Query("user") int userID);

    @POST("/orders/")
    Single<APIResource<Order>> createOrder(@Body CreateOrderRequest order, @Header("Authorization") String accessToken);
}
