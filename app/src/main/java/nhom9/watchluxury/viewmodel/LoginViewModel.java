package nhom9.watchluxury.viewmodel;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import nhom9.watchluxury.data.model.LoginCredentials;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.model.api.APIResource;
import nhom9.watchluxury.data.model.api.ResponseCode;
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

    private final CompositeDisposable disposables = new CompositeDisposable();

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

        disposables.add(
                userRepo.authenticate(user, pass)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new CredentialsObserver())
        );
    }

    private class CredentialsObserver extends DisposableSingleObserver<APIResource<LoginCredentials>> {

        @Override
        public void onSuccess(@NonNull APIResource<LoginCredentials> res) {

            int responseCode = res.getResponseCode();
            if (responseCode == ResponseCode.SUCCESS)
                status.setValue(LoginViewModel.Status.SUCCESS);
            else if (responseCode == ResponseCode.INVALID_LOGIN)
                status.setValue(LoginViewModel.Status.WRONG_LOGIN);
            else
                status.setValue(LoginViewModel.Status.ERROR);

            Log.d("LoginViewModel", "onSuccess: " + res);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            status.setValue(Status.ERROR);
            Log.e("LoginViewModel", "onError: " + e);
        }
    }
}
