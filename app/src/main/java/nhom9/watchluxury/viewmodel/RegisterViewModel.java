package nhom9.watchluxury.viewmodel;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.model.APIResource;
import nhom9.watchluxury.data.model.ResponseCode;
import nhom9.watchluxury.data.repo.UserRepository;

public class RegisterViewModel extends ViewModel {

    public enum Status {
        NONE,
        SUCCESS,
        EMPTY_FIELDS,
        UNMATCHED_PASSWORD,
        ERROR,
    }

    private final MutableLiveData<String> username;
    private final MutableLiveData<String> email;
    private final MutableLiveData<String> address;
    private final MutableLiveData<String> password1;
    private final MutableLiveData<String> password2;
    private final MutableLiveData<Status> status;

    private final UserRepository userRepo;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public RegisterViewModel() {
        userRepo = new UserRepository();

        this.username = new MutableLiveData<>("");
        this.email = new MutableLiveData<>("");
        this.address = new MutableLiveData<>("");
        this.password1 = new MutableLiveData<>("");
        this.password2 = new MutableLiveData<>("");
        this.status = new MutableLiveData<>(Status.NONE);
    }

    public void onRegisterClicked() {

        if (!isValidated())
            return;

        disposables.add(
                userRepo.createUser(
                                username.getValue(), password1.getValue(),
                                email.getValue(), address.getValue()
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new UserObserver())
        );
    }

    private class UserObserver extends DisposableSingleObserver<APIResource<User>> {

        @Override
        public void onSuccess(@NonNull APIResource<User> res) {
            status.setValue(res.getResponseCode() == ResponseCode.SUCCESS ? Status.SUCCESS : Status.ERROR);
            Log.d("RegisterViewModel", "onSuccess: " + res);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            status.setValue(Status.ERROR);
            Log.e("RegisterViewModel", "onError: " + e);
        }
    }

    private boolean isValidated() {

        List<String> inputs = new ArrayList<>(Arrays.asList(
                username.getValue(), email.getValue(),
                address.getValue(),
                password1.getValue(), password2.getValue()
        ));

        if (inputs.stream().anyMatch(TextUtils::isEmpty)) {
            status.setValue(Status.EMPTY_FIELDS);
            return false;
        }

        if (!password1.getValue().equals(password2.getValue())) {
            status.setValue(Status.UNMATCHED_PASSWORD);
            return false;
        }

        return true;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> getAddress() {
        return address;
    }

    public MutableLiveData<String> getPassword1() {
        return password1;
    }

    public MutableLiveData<String> getPassword2() {
        return password2;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
}
