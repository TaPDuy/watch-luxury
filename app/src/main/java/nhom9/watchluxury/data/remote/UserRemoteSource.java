package nhom9.watchluxury.data.remote;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.model.LoginCredentials;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.model.api.APIResource;
import nhom9.watchluxury.data.model.api.ChangePasswordRequest;
import nhom9.watchluxury.data.model.api.LoginRequest;
import nhom9.watchluxury.data.model.api.RegisterRequest;
import nhom9.watchluxury.data.remote.service.AuthService;
import nhom9.watchluxury.data.remote.service.UserService;
import nhom9.watchluxury.util.APIUtils;

public class UserRemoteSource {

    private static final UserService USER_SERVICE = APIUtils.getUserService();
    private static final AuthService AUTH_SERVICE = APIUtils.getAuthenticationService();

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

    private String token() {
        return "Bearer " + TokenManager.getAccessToken();
    }

}
