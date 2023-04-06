package nhom9.watchluxury.data.model;

import androidx.annotation.NonNull;

import com.squareup.moshi.Json;

import nhom9.watchluxury.util.JsonUtils;

public class LoginCredentials {

    @Json(name="access")
    private final String accessToken;
    @Json(name="refresh")
    private final String refreshToken;
    @Json(name="user_id")
    private final int userID;

    public LoginCredentials(String accessToken, String refreshToken, int userID) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userID = userID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public int getLoggedInUserID() {
        return userID;
    }

    @NonNull
    @Override
    public String toString() {
        return "LoginCredentials: " + JsonUtils.toJson(this, LoginCredentials.class);
    }
}
