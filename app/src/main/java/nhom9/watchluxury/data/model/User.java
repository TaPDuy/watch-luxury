package nhom9.watchluxury.data.model;

import androidx.annotation.NonNull;

import com.squareup.moshi.Json;

import java.util.Date;

import nhom9.watchluxury.util.JsonUtils;

public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private String address;
    @Json(name="first_name")
    private String firstName;
    @Json(name="last_name")
    private String lastName;
    @Json(name="phone_number")
    private String phoneNumber;
    @Json(name="is_admin")
    private boolean isAdmin;
    @Json(name="is_active")
    private boolean isActive;
    @Json(name="last_login")
    private transient Date lastLogin;
    @Json(name="date_joined")
    private transient Date dateJoined;

    @NonNull
    @Override
    public String toString() {
        return JsonUtils.toJson(this, User.class);
    }

    public static class Builder {

        User user;

        public Builder() {
            user = new User();
        }

        public User build() {
            return this.user;
        }

        public Builder id(int id) {
            user.id = id;
            return this;
        }

        public Builder username(String username) {
            user.username = username;
            return this;
        }

        public Builder password(String password) {
            user.password = password;
            return this;
        }

        public Builder email(String email) {
            user.email = email;
            return this;
        }

        public Builder address(String address) {
            user.address = address;
            return this;
        }

        public Builder firstName(String firstName) {
            user.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            user.lastName = lastName;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            user.phoneNumber = phoneNumber;
            return this;
        }

        public Builder isAdmin(boolean isAdmin) {
            user.isAdmin = isAdmin;
            return this;
        }

        public Builder isActive(boolean isActive) {
            user.isActive = isActive;
            return this;
        }

        public Builder lastLogin(Date lastLogin) {
            user.lastLogin = lastLogin;
            return this;
        }

        public Builder dateJoined(Date dateJoined) {
            user.dateJoined = dateJoined;
            return this;
        }
    }
}
