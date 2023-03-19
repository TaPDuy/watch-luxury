package nhom9.watchluxury.data.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.squareup.moshi.Types;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import nhom9.watchluxury.BuildConfig;
import nhom9.watchluxury.data.model.LoginCredentials;
import nhom9.watchluxury.data.model.api.APIResponse;
import nhom9.watchluxury.util.JsonUtils;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {

    @Override
    public Request authenticate(Route route, @NonNull okhttp3.Response response) throws IOException {

        String path = response.request().url().encodedPath();

        if (path.equals("/login/"))
            return null;

        if (path.equals("/login/refresh/")) {
            TokenManager.deleteTokens();
            return null;
        }

        if (refreshToken()) {
            Log.d("TokenAuthenticator", "Token refreshed");
            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + TokenManager.getAccessToken())
                    .build();
        }

        Log.d("TokenAuthenticator", "Refresh failed");
        return null;
    }

    private boolean refreshToken() throws IOException {

        // Manually deserializing response because execute() doesn't work

        // Send request
        URL refreshUrl = new URL(BuildConfig.API_BASE_URL + "/login/refresh/");
        HttpURLConnection urlConnection = (HttpURLConnection) refreshUrl.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setUseCaches(false);

        urlConnection.setDoOutput(true);
        String jsonData = "{\"refresh\":\"" + TokenManager.getRefreshToken() + "\"}";
        try(OutputStream os = urlConnection.getOutputStream()) {
            byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = urlConnection.getResponseCode();

        if (responseCode == 200) {

            // Deserializing
            StringBuilder response = new StringBuilder();
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            Type type = Types.newParameterizedType(APIResponse.class, LoginCredentials.class);
            APIResponse<LoginCredentials> res = JsonUtils.fromJson(response.toString(), type);
            LoginCredentials tokens = res.getData();
            TokenManager.saveTokens(tokens.getAccessToken(), tokens.getRefreshToken());
            Log.d("TokenAuthenticator", "New credentials saved: " + tokens);

            return true;
        }

        return false;
    }
}
