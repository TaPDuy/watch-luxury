package nhom9.watchluxury.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import nhom9.watchluxury.data.model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "watchluxury";

    private static AppDatabase db;
    public abstract UserDAO userDAO();

    public static void init(final Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }

    public static AppDatabase getInstance() {
        return db;
    }
}