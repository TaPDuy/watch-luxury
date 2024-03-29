package nhom9.watchluxury.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import nhom9.watchluxury.BuildConfig;
import nhom9.watchluxury.R;
import nhom9.watchluxury.data.remote.RetrofitClient;
import nhom9.watchluxury.data.remote.service.AuthService;
import nhom9.watchluxury.data.remote.service.FavoriteService;
import nhom9.watchluxury.data.remote.service.OrderService;
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

    public static FavoriteService getFavoriteService() {
        return RetrofitClient.getClient(BASE_URL).create(FavoriteService.class);
    }

    public static OrderService getOrderService() {
        return RetrofitClient.getClient(BASE_URL).create(OrderService.class);
    }

    public static void loadImage(String url, ImageView target) {
        Picasso.get().load(BASE_URL + url)
                .placeholder(R.drawable.c1)
                .error(R.mipmap.ic_launcher)
                .into(target);
    }
}
