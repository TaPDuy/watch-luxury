package nhom9.watchluxury.util;

import nhom9.watchluxury.BuildConfig;
import nhom9.watchluxury.data.remote.RetrofitClient;
import nhom9.watchluxury.data.remote.service.AuthService;
import nhom9.watchluxury.data.remote.service.ProductService;
import nhom9.watchluxury.data.remote.service.UserService;

public class APIUtils {

    public static final String BASE_URL = BuildConfig.API_BASE_URL;

    public static AuthService getAuthenticationService() {
        return RetrofitClient.getClient(BASE_URL).create(AuthService.class);
    }

    public static UserService getUserService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static ProductService getProductService() {
        return RetrofitClient.getClient(BASE_URL).create(ProductService.class);
    }
}
