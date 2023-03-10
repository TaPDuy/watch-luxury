package nhom9.watchluxury.data.remote;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static SharedPreferences sharedPref;

    public static void init(Context context) {
        if (sharedPref == null)
            sharedPref = context.getSharedPreferences("login_info", Context.MODE_PRIVATE);
    }

    public static void saveAccessToken(String token) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("accessToken", token);
        editor.apply();
    }

    public static void saveRefreshToken(String token) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("refreshToken", token);
        editor.apply();
    }

    public static void saveUserID(int id) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("userID", id);
        editor.apply();
    }

    public static void saveTokens(String accessToken, String refreshToken) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("accessToken", accessToken);
        editor.putString("refreshToken", refreshToken);
        editor.apply();
    }

    public static void save(String accessToken, String refreshToken, int userID) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("accessToken", accessToken);
        editor.putString("refreshToken", refreshToken);
        editor.putInt("userID", userID);
        editor.apply();
    }

    public static int getUserId() {
        return sharedPref.getInt("userID", -1);
    }

    public static String getAccessToken() {
        return sharedPref.getString("accessToken", "");
    }

    public static String getRefreshToken() {
        return sharedPref.getString("refreshToken", "");
    }

    public static void deleteTokens() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("accessToken");
        editor.remove("refreshToken");
        editor.remove("userID");
        editor.apply();
    }

    public static boolean isAuthenticated() {
        return getUserId() != -1;
    }
}
