package nhom9.watchluxury.viewmodel;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.model.APIResource;
import nhom9.watchluxury.data.remote.model.ChangePasswordRequest;
import nhom9.watchluxury.data.model.ResponseCode;
import nhom9.watchluxury.data.repo.UserRepository;

public class EditUserViewModel extends ViewModel {

    public enum Status {
        NONE,
        SUCCESS,
        ERROR,
        NO_USER,
    }

    private final HashMap<String, MutableLiveData<String>> errors;
    private final HashMap<String, MutableLiveData<String>> passwordErrors;

    private final MutableLiveData<Status> status;

    private final UserRepository userRepo;
    private final MutableLiveData<User> user;
    private final MutableLiveData<ChangePasswordRequest> changePassword;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public EditUserViewModel(User user) {

        userRepo = new UserRepository();
        this.status = new MutableLiveData<>(Status.NONE);

        this.user = new MutableLiveData<>(null);
        this.changePassword = new MutableLiveData<>(new ChangePasswordRequest("", ""));

        this.errors = new HashMap<>();
        this.errors.put("email", new MutableLiveData<>(null));
        this.errors.put("address", new MutableLiveData<>(null));
        this.errors.put("phoneNumber", new MutableLiveData<>(null));

        this.passwordErrors = new HashMap<>();
        this.passwordErrors.put("password1", new MutableLiveData<>(null));
        this.passwordErrors.put("password2", new MutableLiveData<>(null));

        if (user == null) {
            this.status.setValue(Status.NO_USER);
        } else {
            this.user.setValue(user);
        }
    }

    private boolean isValidated() {

        String email = Objects.requireNonNull(this.user.getValue()).getEmail();
        String address = Objects.requireNonNull(this.user.getValue().getAddress());
        String phone = Objects.requireNonNull(this.user.getValue().getPhoneNumber());

        if (TextUtils.isEmpty(email)) {
            errors.get("email").setValue("Email is required");
        } else {
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                errors.get("email").setValue("Invalid email");
            } else {
                errors.get("email").setValue(null);
            }
        }

        if (TextUtils.isEmpty(address)) {
            errors.get("address").setValue("Address is required");
        } else {
            errors.get("address").setValue(null);
        }

        if (!TextUtils.isDigitsOnly(phone)) {
            errors.get("phoneNumber").setValue("Invalid phone number");
        } else {
            errors.get("phoneNumber").setValue(null);
        }

        return errors.values().stream().allMatch(data -> data.getValue() == null);
    }

    public void onSaveClicked() {

        if (!isValidated())
            return;

        disposables.add(
                userRepo.updateUser(
                                Objects.requireNonNull(user.getValue()).getId(),
                                this.user.getValue()
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
            Log.d("EditUserViewModel", "onSuccess: " + res);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            status.setValue(Status.ERROR);
            Log.e("EditUserViewModel", "onError: " + e);
        }
    }

    private boolean validatePasswords() {

        String pw1 = Objects.requireNonNull(changePassword.getValue()).getOldPassword();
        String pw2 = Objects.requireNonNull(changePassword.getValue()).getNewPassword();
        String errPw1 = "";
        String errPw2 = "";

        if (TextUtils.isEmpty(pw2))
            errPw2 += "New password is required";

        if (TextUtils.isEmpty(pw1))
            errPw1 += "Old password is required";
        else {
            if (pw1.equals(pw2))
                errPw2 += "New password should be different";
        }

        passwordErrors.get("password1").setValue(errPw1.isEmpty() ? null : errPw1);
        passwordErrors.get("password2").setValue(errPw2.isEmpty() ? null : errPw2);

        return passwordErrors.values().stream().allMatch(data -> data.getValue() == null);
    }

    public void onPasswordSaveClicked() {

        if (!validatePasswords())
            return;

        disposables.add(
                userRepo.updatePassword(
                                Objects.requireNonNull(user.getValue()).getId(),
                                changePassword.getValue().getOldPassword(),
                                changePassword.getValue().getNewPassword()
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new PasswordObserver())
        );
    }

    private class PasswordObserver extends DisposableSingleObserver<APIResource<Object>> {

        @Override
        public void onSuccess(@NonNull APIResource<Object> res) {

            if (res.getResponseCode() == ResponseCode.WRONG_PASSWORD) {
                passwordErrors.get("password1").setValue("Wrong password");
            } else {
                passwordErrors.get("password1").setValue(null);
                status.setValue(res.getResponseCode() == ResponseCode.SUCCESS ? Status.SUCCESS : Status.ERROR);
            }

            Log.d("EditUserViewModel", "onSuccess: " + res);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            status.setValue(Status.ERROR);
            Log.e("EditUserViewModel", "onError: " + e);
        }
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<ChangePasswordRequest> getPasswords() {
        return changePassword;
    }

    public HashMap<String, MutableLiveData<String>> getErrors() {
        return errors;
    }

    public HashMap<String, MutableLiveData<String>> getPasswordErrors() {
        return passwordErrors;
    }
}
