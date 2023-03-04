package nhom9.watchluxury.util;

import nhom9.watchluxury.BuildConfig;
import nhom9.watchluxury.data.remote.RetrofitClient;
import nhom9.watchluxury.data.remote.service.AuthService;

public class APIUtils {

    public static final String BASE_URL = BuildConfig.API_BASE_URL;

    public static AuthService getAuthenticationService() {
        return RetrofitClient.getClient(BASE_URL).create(AuthService.class);
    }
}
