package nhom9.watchluxury.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.local.model.OrderItem;
import nhom9.watchluxury.data.local.model.OrderRow;

@Dao
public interface OrderDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(OrderRow order);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(OrderItem item);

    @Query("SELECT * FROM tbl_order WHERE user_id = :userID")
    Single<List<OrderRow>> get(int userID);
}
