package nhom9.watchluxury.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

public class SettingsUtils {

    private static SharedPreferences sharedPref;

    public static void init(final Context context) {
        sharedPref = context.getSharedPreferences("settings", Context.MODE_PRIVATE);

        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        setDarkMode(sharedPref.getBoolean("darkMode", nightModeFlags == Configuration.UI_MODE_NIGHT_YES));
    }

    public static void setDarkMode(boolean b) {
        AppCompatDelegate.setDefaultNightMode(b ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("darkMode", b);
        editor.apply();
    }

    public static boolean isDarkMode() {
        return sharedPref.getBoolean("darkMode", false);
    }
}
