package nhom9.watchluxury.data.remote;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.model.api.APIResponse;
import nhom9.watchluxury.data.remote.service.UserService;
import nhom9.watchluxury.util.APIUtils;

public class UserRemoteSource {

    private static final UserService USER_SERVICE = APIUtils.getUserService();
    private static final String FAIL_MSG = "Request failed";

    private static UserRemoteSource self = null;

    public static UserRemoteSource get() {
        if (self == null)
            self = new UserRemoteSource();
        return self;
    }

    public Single<APIResponse<User>> getUser(int id) {
        return USER_SERVICE.getUser(id, "Bearer " + TokenManager.getAccessToken());
    }

//    private <T> APIResponse<T> handleResponse(Response<APIResponse<T>> responsePackage) {
//
//        if (responsePackage.isSuccessful()) {
//            APIResponse<T> response = responsePackage.body();
//            assert response != null;
//            return response;
//        }
//
//        Log.d("UserRepo", "Request failed (" + responsePackage.code() + ")");
//
//        // Deserializing response
//        int code = ResponseCode.FAILURE;
//        String msg = FAIL_MSG;
//        try {
//
//            assert responsePackage.errorBody() != null;
//            JSONObject error = new JSONObject(responsePackage.errorBody().string());
//            msg = error.getString("msg");
//            code = error.getInt("code");
//        } catch (JSONException | IOException e) {
//            Log.d("UserRepo", e.getMessage());
//        }
//
//        return new APIResponse<>(code, msg, null);
//    }
//
//    public interface Callback<T> {
//        void onResponse(int responseCode, T data, String msg);
//    }
}
