package nhom9.watchluxury.data.model;

import androidx.annotation.NonNull;

import com.squareup.moshi.Json;

import java.io.Serializable;

import nhom9.watchluxury.util.JsonUtils;

public class User implements Serializable {

    private int id;
    private String username;
    private String email;
    private String address;
    @Json(name="first_name")
    private String firstName;
    @Json(name="last_name")
    private String lastName;
    @Json(name="phone_number")
    private String phoneNumber;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullname() {
        return this.getFirstName() + " " + this.getLastName();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return JsonUtils.toJson(this, User.class);
    }

    public static class Builder {

        final User user;

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
    }
}
