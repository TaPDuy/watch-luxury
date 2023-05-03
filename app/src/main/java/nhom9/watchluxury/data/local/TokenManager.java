package nhom9.watchluxury.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import nhom9.watchluxury.data.model.User;

public class TokenManager {

    private static SharedPreferences sharedPref;
    private static User user;

    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_PHONE = "phoneNumber";
    public static final String KEY_EMAIL = "email";

    public static final String KEY_ACCESS = "accessToken";
    public static final String KEY_REFRESH = "refreshToken";

    public static void init(Context context) {
        if (sharedPref == null)
            sharedPref = context.getSharedPreferences("login_info", Context.MODE_PRIVATE);
    }

    public static void saveUser(User user) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_ADDRESS, user.getAddress());
        editor.putString(KEY_FIRST_NAME, user.getFirstName());
        editor.putString(KEY_LAST_NAME, user.getLastName());
        editor.putString(KEY_PHONE, user.getPhoneNumber());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.apply();

        TokenManager.user = user;
    }

    public static String getString(String key) {
        return sharedPref.getString(key, "");
    }

    public static void saveAccessToken(String token) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_ACCESS, token);
        editor.apply();
    }

    public static void saveRefreshToken(String token) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_REFRESH, token);
        editor.apply();
    }

    public static void saveTokens(String accessToken, String refreshToken) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_ACCESS, accessToken);
        editor.putString(KEY_REFRESH, refreshToken);
        editor.apply();
    }

    public static int getUserId() {
        return sharedPref.getInt(KEY_ID, -1);
    }

    public static String getUsername() {
        return sharedPref.getString(KEY_USERNAME, "");
    }

    public static String getAccessToken() {
        return sharedPref.getString(KEY_ACCESS, "");
    }

    public static String getRefreshToken() {
        return sharedPref.getString(KEY_REFRESH, "");
    }

    public static void deleteTokens() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();

        TokenManager.user = null;
    }

    public static boolean isAuthenticated() {
        return !getAccessToken().isEmpty() && getUserId() != -1;
    }

    public static User getUser() {
        return user;
    }
}
