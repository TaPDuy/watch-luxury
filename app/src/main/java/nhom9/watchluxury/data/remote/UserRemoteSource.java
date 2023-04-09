package nhom9.watchluxury.data.remote;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.local.TokenManager;
import nhom9.watchluxury.data.model.LoginCredentials;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.remote.model.ChangePasswordRequest;
import nhom9.watchluxury.data.remote.model.LoginRequest;
import nhom9.watchluxury.data.remote.model.RegisterRequest;
import nhom9.watchluxury.data.remote.service.AuthService;
import nhom9.watchluxury.data.remote.service.FavoriteService;
import nhom9.watchluxury.data.remote.service.UserService;
import nhom9.watchluxury.util.APIUtils;

public class UserRemoteSource {

    private static final UserService USER_SERVICE = APIUtils.getUserService();
    private static final AuthService AUTH_SERVICE = APIUtils.getAuthenticationService();
    private static final FavoriteService FAVORITE_SERVICE = APIUtils.getFavoriteService();

    public Single<APIResource<User>> getUser(int id) {
        return USER_SERVICE.getUser(id, token());
    }

    public Single<APIResource<User>> updateUser(int id, User user) {
        return USER_SERVICE.updateUser(id, user, token());
    }

    public Single<APIResource<User>> createUser(String username, String password, String email, String address) {
        return AUTH_SERVICE.register(new RegisterRequest(username, password, email, address));
    }

    public Single<APIResource<LoginCredentials>> authenticate(String username, String password) {
        return AUTH_SERVICE.login(new LoginRequest(username, password));
    }

    public Single<APIResource<Object>> updatePassword(int id, String oldPassword, String newPassword) {
        return USER_SERVICE.changePassword(id, new ChangePasswordRequest(oldPassword, newPassword), token());
    }

    public Single<APIResource<List<Product>>> getFavorites(int id) {
        return FAVORITE_SERVICE.getUserFavorites(id, token());
    }

    private String token() {
        return "Bearer " + TokenManager.getAccessToken();
    }

}
