package nhom9.watchluxury.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.model.User;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(User user);

    @Delete
    Single<Integer> deleteAll(User user);

    @Query("SELECT * FROM tbl_user WHERE id = :id")
    Single<User> get(int id);
}
