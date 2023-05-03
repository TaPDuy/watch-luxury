package nhom9.watchluxury.data.local.source;

import android.util.Log;

import java.util.Objects;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.local.AppDatabase;
import nhom9.watchluxury.data.local.dao.UserDAO;
import nhom9.watchluxury.data.model.User;

public class UserLocalSource extends LocalSource {

    private static final UserDAO dao = AppDatabase.getInstance().userDAO();

    // For logging
    private static final String CLASS_NAME = "UserDAO";

    public Single<User> getUser(int id) {
        return dao.get(id)
                .doOnSuccess(user -> Log.d(CLASS_NAME, "Query: " + user))
                .doOnError(throwable -> Log.e(CLASS_NAME, Objects.requireNonNull(throwable.getMessage())));
    }

    public Completable insertUser(User user) {
        return dao.insert(user)
                .doOnComplete(() -> Log.d(CLASS_NAME, "Insert: " + user));
    }
}
