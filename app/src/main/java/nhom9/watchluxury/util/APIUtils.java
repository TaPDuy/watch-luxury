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
import nhom9.watchluxury.data.remote.service.WeatherService;

public class APIUtils {

    private static final RetrofitClient client = new RetrofitClient(BuildConfig.API_BASE_URL);
    private static final RetrofitClient weatherClient = new RetrofitClient(BuildConfig.WEATHER_API_BASE_URL);

    public static AuthService getAuthenticationService() {
        return client.get().create(AuthService.class);
    }

    public static UserService getUserService() {
        return client.get().create(UserService.class);
    }

    public static ProductService getProductService() {
        return client.get().create(ProductService.class);
    }

    public static FavoriteService getFavoriteService() {
        return client.get().create(FavoriteService.class);
    }

    public static OrderService getOrderService() {
        return client.get().create(OrderService.class);
    }

    public static WeatherService getWeatherService() {
        return weatherClient.get().create(WeatherService.class);
    }

    public static void loadImage(String url, ImageView target) {
        Picasso.get().load(client.getBaseUrl() + url)
                .placeholder(R.drawable.c1)
                .error(R.mipmap.ic_launcher)
                .into(target);
    }
}
