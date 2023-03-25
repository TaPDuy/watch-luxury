package nhom9.watchluxury.data.local;

import android.util.Log;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.model.User;

public class UserLocalSource {

    private static final UserDAO dao = AppDatabase.getInstance().userDAO();

    // For logging
    private static final String CLASS_NAME = "UserDAO";

    public Single<User> getUser(int id) {
        return dao.get(id)
                .doOnSuccess(user -> Log.d(CLASS_NAME, "Query: " + user))
                .doOnError(throwable -> Log.e(CLASS_NAME, throwable.getMessage()));
    }

    public Completable insertUser(User user) {
        return dao.insert(user)
                .doOnComplete(() -> Log.d(CLASS_NAME, "Insert: " + user));
    }
}
