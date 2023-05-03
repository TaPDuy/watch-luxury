package nhom9.watchluxury.data.repo;

import android.util.Log;

import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.data.DataSource;
import nhom9.watchluxury.data.local.source.UserLocalSource;
import nhom9.watchluxury.data.model.LoginCredentials;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.remote.model.ResponseCode;
import nhom9.watchluxury.data.local.TokenManager;
import nhom9.watchluxury.data.remote.source.UserRemoteSource;

public class UserRepository {

    private static final UserRemoteSource userAPI = DataSource.get(UserRemoteSource.class);
    private static final UserLocalSource userDB = DataSource.get(UserLocalSource.class);

    // For logging
    private static final String CLASS_NAME = "UserRepo";

    public Flowable<APIResource<User>> getUser(int id) {

        Single<APIResource<User>> localData = userDB.getUser(id)
                .map(user -> new APIResource<>(ResponseCode.SUCCESS, "Local data", user));

        Single<APIResource<User>> remoteData = userAPI.getUser(id)
                .doOnSuccess(
                        res -> {
                            User user = res.getData();
                            userDB.insertUser(user).subscribe().dispose();
                        }
                )
                .doOnError(throwable -> Log.e(CLASS_NAME, Objects.requireNonNull(throwable.getMessage())));

        return Single.concatArrayDelayError(localData, remoteData);
    }

    public Single<APIResource<User>> createUser(String username, String password, String email, String address) {

        return userAPI.createUser(username, password, email, address)
                .doOnSuccess(res -> Log.d(CLASS_NAME, "Created: " + res.getData()))
                .doOnError(throwable -> Log.e(CLASS_NAME, Objects.requireNonNull(throwable.getMessage())));
    }

    public Single<APIResource<User>> updateUser(int id, User user) {

        return userAPI.updateUser(id, user)
                .doOnSuccess(
                        res -> {
                            User updated = res.getData();
                            userDB.insertUser(updated).subscribe().dispose();
                        }
                )
                .doOnError(throwable -> Log.e(CLASS_NAME, Objects.requireNonNull(throwable.getMessage())));
    }

    public Single<APIResource<LoginCredentials>> authenticate(String username, String password) {

        return userAPI.authenticate(username, password)
                .doOnSuccess(res -> {

                    LoginCredentials credentials = res.getData();
                    TokenManager.saveTokens(
                            credentials.getAccessToken(),
                            credentials.getRefreshToken()
                    );
                    TokenManager.saveInt(TokenManager.KEY_ID, credentials.getLoggedInUserID());

                    userAPI.getUser(credentials.getLoggedInUserID())
                            .doOnSuccess(
                                    res2 -> {
                                        User user = res2.getData();
                                        TokenManager.saveUser(user);
                                        userDB.insertUser(user).subscribe().dispose();
                                    }
                            )
                            .doOnError(throwable -> Log.e(CLASS_NAME, Objects.requireNonNull(throwable.getMessage())))
                            .subscribe().dispose();

                    Log.d(CLASS_NAME, "New credentials saved: " + credentials);
                })
                .doOnError(throwable -> Log.e(CLASS_NAME, Objects.requireNonNull(throwable.getMessage())));
    }

    public Single<APIResource<Object>> updatePassword(int id, String oldPassword, String newPassword) {

        return userAPI.updatePassword(id, oldPassword, newPassword)
                .doOnSuccess(res -> Log.d(CLASS_NAME, "Updated password"))
                .doOnError(throwable -> Log.e(CLASS_NAME, Objects.requireNonNull(throwable.getMessage())));
    }

    public Single<APIResource<List<Product>>> getFavorites(int id) {
        return userAPI.getFavorites(id)
                .doOnSuccess(res -> Log.d(CLASS_NAME, "Fetched favorites"))
                .doOnError(throwable -> Log.e(CLASS_NAME, Objects.requireNonNull(throwable.getMessage())));
    }
}
