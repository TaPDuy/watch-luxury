package nhom9.watchluxury.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import nhom9.watchluxury.data.repo.UserRepository;

public class LoginViewModel extends ViewModel {

    public enum Status {
        NONE,
        SUCCESS,
        USERNAME_EMPTY,
        PASSWORD_EMPTY,
        ERROR,
        WRONG_LOGIN,
    }

    private final MutableLiveData<String> username;
    private final MutableLiveData<String> password;
    private final MutableLiveData<Status> status;

    private final UserRepository userRepo;

    public LoginViewModel() {
        userRepo = new UserRepository();

        this.username = new MutableLiveData<>("");
        this.password = new MutableLiveData<>("");
        this.status = new MutableLiveData<>(Status.NONE);
    }

    public MutableLiveData<Status> getStatus() {
        return this.status;
    }

    public MutableLiveData<String> getUsername() {
        return this.username;
    }

    public MutableLiveData<String> getPassword() {
        return this.password;
    }

    public void onLoginClicked() {

        String user = username.getValue();
        String pass = password.getValue();

        if(TextUtils.isEmpty(user)){
            status.setValue(Status.USERNAME_EMPTY);
            return;
        }
        if(TextUtils.isEmpty(pass)){
            status.setValue(Status.PASSWORD_EMPTY);
            return;
        }

        userRepo.authenticate(user, pass, (responseCode, id) -> {
            if (id != null)
                status.setValue(LoginViewModel.Status.SUCCESS);
            else if (responseCode >= 400 && responseCode < 500)
                status.setValue(LoginViewModel.Status.WRONG_LOGIN);
            else
                status.setValue(LoginViewModel.Status.ERROR);
        });
    }
}
