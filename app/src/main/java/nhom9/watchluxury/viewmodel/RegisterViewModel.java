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
import nhom9.watchluxury.data.remote.service.AuthService;
import nhom9.watchluxury.util.APIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private final AuthService authService;

    public RegisterViewModel() {
        authService = APIUtils.getAuthenticationService();

        this.username = new MutableLiveData<>("");
        this.email = new MutableLiveData<>("");
        this.address = new MutableLiveData<>("");
        this.password1 = new MutableLiveData<>("");
        this.password2 = new MutableLiveData<>("");
        this.status = new MutableLiveData<>(Status.NONE);
    }

    public void onRegisterClicked() {

        String username = this.username.getValue();
        String email = this.email.getValue();
        String address = this.address.getValue();
        String password1 = this.password1.getValue();
        String password2 = this.password2.getValue();

        List<String> inputs = new ArrayList<>(Arrays.asList(
                username, email, address, password1, password2
        ));

        if (inputs.stream().anyMatch(TextUtils::isEmpty)) {
            status.setValue(Status.EMPTY_FIELDS);
            return;
        }

        if (!password1.equals(password2)) {
            status.setValue(Status.UNMATCHED_PASSWORD);
            return;
        }

        authService.register(
                new User.Builder()
                        .username(username)
                        .email(email)
                        .address(address)
                        .password(password1)
                        .build()
        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User data = response.body();
                    status.setValue(Status.SUCCESS);
                    Log.d("RegisterActivity", data.toString());
                } else {
                    status.setValue(Status.ERROR);
                    Log.d("RegisterActivity", "Couldn't register (" + response.code() + ")");
                    Log.d("RegisterActivity", call.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                status.setValue(Status.ERROR);
                Log.d("LoginActivity", "Couldn't register");
                Log.d("LoginActivity", call.toString());
                Log.d("LoginActivity", t.getMessage());
            }
        });
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
