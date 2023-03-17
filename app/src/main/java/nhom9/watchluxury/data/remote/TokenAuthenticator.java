package nhom9.watchluxury.data.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import nhom9.watchluxury.data.model.LoginCredentials;
import nhom9.watchluxury.data.model.api.APIResponse;
import nhom9.watchluxury.data.remote.service.AuthService;
import nhom9.watchluxury.data.remote.service.ServiceHolder;
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
        Response<APIResponse<LoginCredentials>> responsePackage = service.refresh(
                TokenManager.getRefreshToken()
        ).execute();

        APIResponse<LoginCredentials> response = responsePackage.body();
        assert response != null;

        if (responsePackage.isSuccessful()) {
            LoginCredentials tokens = response.getData();
            TokenManager.saveTokens(tokens.getAccessToken(), tokens.getRefreshToken());
            return true;
        } else {
            Log.d("RetrofitClient", response.getMessage());
            return false;
        }
    }
}
