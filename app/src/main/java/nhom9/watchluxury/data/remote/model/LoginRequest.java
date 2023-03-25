package nhom9.watchluxury.data.remote.model;

import androidx.annotation.NonNull;

import nhom9.watchluxury.util.JsonUtils;

public class LoginRequest {

    private String username;
    private String password;

    public LoginRequest() {
        this("", "");
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return "LoginRequest: " + JsonUtils.toJson(this, LoginRequest.class);
    }
}
