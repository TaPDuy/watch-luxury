package nhom9.watchluxury.util;

import nhom9.watchluxury.data.local.ProductLocalSource;
import nhom9.watchluxury.data.local.UserLocalSource;
import nhom9.watchluxury.data.remote.OrderRemoteSource;
import nhom9.watchluxury.data.remote.ProductRemoteSource;
import nhom9.watchluxury.data.remote.UserRemoteSource;

public class DataSource {

    private static UserLocalSource userLocalSource;
    private static ProductLocalSource productLocalSource;
    private static UserRemoteSource userRemoteSource;
    private static ProductRemoteSource productRemoteSource;
    private static OrderRemoteSource orderRemoteSource;

    public static UserLocalSource getLocalUser() {
        if (userLocalSource == null)
            userLocalSource = new UserLocalSource();
        return userLocalSource;
    }

    public static ProductLocalSource getLocalProduct() {
        if (productLocalSource == null)
            productLocalSource = new ProductLocalSource();
        return productLocalSource;
    }

    public static UserRemoteSource getRemoteUser() {
        if (userRemoteSource == null)
            userRemoteSource = new UserRemoteSource();
        return userRemoteSource;
    }

    public static ProductRemoteSource getRemoteProduct() {
        if (productRemoteSource == null)
            productRemoteSource = new ProductRemoteSource();
        return productRemoteSource;
    }

    public static OrderRemoteSource getRemoteOrder() {
        if (orderRemoteSource == null)
            orderRemoteSource = new OrderRemoteSource();
        return orderRemoteSource;
    }
}
