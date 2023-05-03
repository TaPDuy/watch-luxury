package nhom9.watchluxury.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import nhom9.watchluxury.data.local.dao.OrderDAO;
import nhom9.watchluxury.data.local.dao.ProductDAO;
import nhom9.watchluxury.data.local.dao.UserDAO;
import nhom9.watchluxury.data.local.model.OrderItem;
import nhom9.watchluxury.data.local.model.OrderRow;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.data.model.User;

@Database(entities = {
        User.class,
        Product.class,
        OrderRow.class,
        OrderItem.class
}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "watchluxury";

    private static AppDatabase db;
    public abstract UserDAO userDAO();
    public abstract ProductDAO productDAO();
    public abstract OrderDAO orderDAO();

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
