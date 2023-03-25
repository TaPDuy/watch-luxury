package nhom9.watchluxury.data.repo;

import android.util.Log;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.local.AppDatabase;
import nhom9.watchluxury.data.local.UserDAO;
import nhom9.watchluxury.data.model.LoginCredentials;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.model.api.APIResponse;
import nhom9.watchluxury.data.remote.TokenManager;
import nhom9.watchluxury.data.remote.UserRemoteSource;

public class UserRepository {

    private static final UserRemoteSource userAPI = UserRemoteSource.get();
    private static final UserDAO userDB = AppDatabase.getInstance().userDAO();

    // For logging
    private static final String CLASS_NAME = "UserRepo";

    public Flowable<User> getUser(int id) {

        Single<User> localData = userDB.get(id)
                .doOnSuccess(user -> Log.d(CLASS_NAME, "Query: " + user))
                .doOnError(throwable -> Log.e(CLASS_NAME, throwable.getMessage()));

        Single<User> remoteData = userAPI.getUser(id)
                .map(APIResponse::getData)
                .doOnSuccess(
                        user -> userDB.insert(user).subscribe(
                                () -> Log.d(CLASS_NAME, "Cached: " + user)
                        ).dispose()
                )
                .doOnError(throwable -> Log.e(CLASS_NAME, throwable.getMessage()));

        return Single.concatArrayDelayError(localData, remoteData);
    }

    public Single<User> createUser(String username, String password, String email, String address) {

        return userAPI.createUser(username, password, email, address)
                .map(APIResponse::getData)
                .doOnSuccess(user -> Log.d(CLASS_NAME, "Created: " + user))
                .doOnError(throwable -> Log.e(CLASS_NAME, throwable.getMessage()));
    }

    public Single<User> updateUser(int id, User user) {

        return userAPI.updateUser(id, user)
                .map(APIResponse::getData)
                .doOnSuccess(
                        res -> userDB.insert(res).subscribe(
                                () -> Log.d(CLASS_NAME, "Updated: " + res)
                        ).dispose()
                )
                .doOnError(throwable -> Log.e(CLASS_NAME, throwable.getMessage()));
    }

    public Single<LoginCredentials> authenticate(String username, String password) {

        return userAPI.authenticate(username, password)
                .map(APIResponse::getData)
                .doOnSuccess(credentials -> {
                    TokenManager.save(
                            credentials.getAccessToken(),
                            credentials.getRefreshToken(),
                            credentials.getLoggedInUserID()
                    );

                    Log.d(CLASS_NAME, "New credentials saved: " + credentials);
                })
                .doOnError(throwable -> Log.e(CLASS_NAME, throwable.getMessage()));
    }

    public Single<Object> updatePassword(int id, String oldPassword, String newPassword) {

        return userAPI.updatePassword(id, oldPassword, newPassword)
                .map(APIResponse::getData)
                .doOnSuccess(user -> Log.d(CLASS_NAME, "Updated password"))
                .doOnError(throwable -> Log.e(CLASS_NAME, throwable.getMessage()));
    }
}
