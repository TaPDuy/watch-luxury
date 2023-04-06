package nhom9.watchluxury.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.model.Product;

@Dao
public interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Product product);

    @Query("SELECT * FROM tbl_product WHERE id = :id")
    Single<Product> get(int id);
}
