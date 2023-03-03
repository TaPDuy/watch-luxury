package nhom9.watchluxury.data.model;

import androidx.annotation.NonNull;

public class LoginResponse {

    private String accessToken;
    private String refreshToken;

    public LoginResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
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

    @NonNull
    @Override
    public String toString() {
        return this.accessToken + " - " + this.refreshToken;
    }
}
