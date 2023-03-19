package nhom9.watchluxury.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Objects;

import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.repo.UserRepository;

public class EditUserViewModel extends ViewModel {

    public enum Status {
        NONE,
        SUCCESS,
        ERROR,
        NO_USER,
    }

    private final HashMap<String, MutableLiveData<String>> errors;

    private final MutableLiveData<Status> status;

    private final UserRepository userRepo;
    private final MutableLiveData<User> user;

    public EditUserViewModel(User user) {
        userRepo = new UserRepository();
        this.status = new MutableLiveData<>(Status.NONE);
        this.user = new MutableLiveData<>(null);

        this.errors = new HashMap<>();
        this.errors.put("email", new MutableLiveData<>(null));
        this.errors.put("address", new MutableLiveData<>(null));
        this.errors.put("phoneNumber", new MutableLiveData<>(null));

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

        if(isValidated()) {
            userRepo.updateUser(
                    Objects.requireNonNull(user.getValue()).getId(),
                    this.user.getValue(),
                    (responseCode, res, msg) -> status.setValue(res != null ? Status.SUCCESS : Status.ERROR)
            );
        }
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public HashMap<String, MutableLiveData<String>> getErrors() {
        return errors;
    }
}
