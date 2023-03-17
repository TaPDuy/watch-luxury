package nhom9.watchluxury.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.remote.TokenManager;
import nhom9.watchluxury.data.repo.UserRepository;

public class UserInfoViewModel extends ViewModel {

    public enum Status {
        NONE,
        ERROR,
        SUCCESS,
    }

    private final MutableLiveData<User> user;
    private final MutableLiveData<Status> status;
    private final UserRepository userRepo;

    public UserInfoViewModel() {
        userRepo = new UserRepository();
        loadUserInfo();

        user = new MutableLiveData<>(null);
        status = new MutableLiveData<>(Status.NONE);
    }

    public MutableLiveData<User> getUser() {
        return this.user;
    }

    public MutableLiveData<Status> getStatus() {
        return this.status;
    }

    public void onReload() {
        loadUserInfo();
    }
    private void loadUserInfo() {

        if (!TokenManager.isAuthenticated())
            return;

        userRepo.getUser(TokenManager.getUserId(), (responseCode, res, msg) -> {
            if (res != null) {
                user.setValue(res);
                status.setValue(Status.SUCCESS);
            } else {
                user.setValue(null);
                status.setValue(Status.ERROR);
            }
        });
    }
}
