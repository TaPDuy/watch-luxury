package nhom9.watchluxury.viewmodel;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.remote.service.UserService;
import nhom9.watchluxury.util.APIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserViewModel extends ViewModel {

    public enum Status {
        NONE,
        SUCCESS,
        ERROR,
        NO_USER,
        EMPTY_ADDRESS,
        EMPTY_EMAIL,
        INVALID_PHONE,
    }

    private final MutableLiveData<String> firstName;
    private final MutableLiveData<String> lastName;
    private final MutableLiveData<String> email;
    private final MutableLiveData<String> address;
    private final MutableLiveData<String> phoneNumber;

    private final MutableLiveData<Status> status;
    private final UserService userService;
    private User user;

    public EditUserViewModel(User user) {
        userService = APIUtils.getUserService();
        this.status = new MutableLiveData<>(Status.NONE);

        this.firstName = new MutableLiveData<>("");
        this.lastName = new MutableLiveData<>("");
        this.email = new MutableLiveData<>("");
        this.address = new MutableLiveData<>("");
        this.phoneNumber = new MutableLiveData<>("");

        if (user == null) {
            this.status.setValue(Status.NO_USER);
        } else {
            this.user = user;
            this.firstName.setValue(user.getFirstName());
            this.lastName.setValue(user.getLastName());
            this.email.setValue(user.getEmail());
            this.address.setValue(user.getAddress());
            this.phoneNumber.setValue(user.getPhoneNumber());
        }
    }

    private boolean isValidated() {

        if (TextUtils.isEmpty(address.getValue())) {
            status.setValue(Status.EMPTY_ADDRESS);
            return false;
        }

        if (TextUtils.isEmpty(email.getValue())) {
            status.setValue(Status.EMPTY_EMAIL);
            return false;
        }

        if (!TextUtils.isDigitsOnly(phoneNumber.getValue())) {
            status.setValue(Status.INVALID_PHONE);
            return false;
        }

        return !firstName.getValue().equals(user.getFirstName()) ||
                !lastName.getValue().equals(user.getLastName()) ||
                !email.getValue().equals(user.getEmail()) ||
                !address.getValue().equals(user.getAddress()) ||
                !phoneNumber.getValue().equals(user.getPhoneNumber());
    }

    public void onSaveClicked() {

        if (!isValidated())
            return;

        userService.updateUser(user.getId(),
                new User.Builder()
                        .firstName(firstName.getValue())
                        .lastName(lastName.getValue())
                        .email(email.getValue())
                        .address(address.getValue())
                        .phoneNumber(phoneNumber.getValue())
                        .build()
        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    status.setValue(Status.SUCCESS);
                } else {
                    status.setValue(Status.ERROR);
                    Log.d("EditUserActivity", "Couldn't register (" + response.code() + ")");
                    Log.d("EditUserActivity", call.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                status.setValue(Status.ERROR);
                Log.d("EditUserActivity", "Couldn't register");
                Log.d("EditUserActivity", call.toString());
                Log.d("EditUserActivity", t.getMessage());
            }
        });
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<String> getFirstName() {
        return firstName;
    }

    public MutableLiveData<String> getLastName() {
        return lastName;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> getAddress() {
        return address;
    }

    public MutableLiveData<String> getPhoneNumber() {
        return phoneNumber;
    }
}
