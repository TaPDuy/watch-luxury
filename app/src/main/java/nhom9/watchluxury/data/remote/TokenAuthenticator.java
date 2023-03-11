package nhom9.watchluxury.data.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import nhom9.watchluxury.data.model.LoginInfo;
import nhom9.watchluxury.data.model.LoginResponse;
import nhom9.watchluxury.data.remote.service.AuthService;
import nhom9.watchluxury.data.remote.service.ServiceHolder;
import nhom9.watchluxury.util.APIUtils;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Route;
import retrofit2.Response;

public class TokenAuthenticator implements Authenticator {

    private final ServiceHolder serviceHolder;

    public TokenAuthenticator(ServiceHolder serviceHolder) {
        this.serviceHolder = serviceHolder;
    }

    @Override
    public Request authenticate(Route route, @NonNull okhttp3.Response response) throws IOException {

        if (response.request().url().encodedPath().equals("/login/refresh/")) {
            TokenManager.deleteTokens();
            return null;
        }

        if (refreshToken()) {
            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + TokenManager.getAccessToken())
                    .build();
        }

        return null;
    }

    private boolean refreshToken() throws IOException {

        Log.d("RetrofitClient", "Refreshing tokens...");
        AuthService service = (AuthService) serviceHolder.getService();
        Response<LoginResponse> response = service.refresh(
                new LoginResponse(TokenManager.getRefreshToken())
        ).execute();

        if (response.isSuccessful()) {
            TokenManager.saveTokens(response.body().getAccessToken(), response.body().getRefreshToken());
            return true;
        } else {
            Log.d("RetrofitClient", response.message());
            return false;
        }
    }
}
