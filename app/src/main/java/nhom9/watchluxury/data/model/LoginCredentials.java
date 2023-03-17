package nhom9.watchluxury.data.model;

import androidx.annotation.NonNull;

import com.squareup.moshi.Json;

import nhom9.watchluxury.util.JsonUtils;

public class LoginCredentials {

    @Json(name="access")
    private String accessToken;
    @Json(name="refresh")
    private String refreshToken;
    @Json(name="user_id")
    private int userID;

    public LoginCredentials(String refreshToken) {
        this("", refreshToken, -1);
    }

    public LoginCredentials(String accessToken, String refreshToken, int userID) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userID = userID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getLoggedInUserID() {
        return userID;
    }

    public void setLoggedInUserID(int id) {
        this.userID = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "LoginCredentials: " + JsonUtils.toJson(this, LoginCredentials.class);
    }
}
