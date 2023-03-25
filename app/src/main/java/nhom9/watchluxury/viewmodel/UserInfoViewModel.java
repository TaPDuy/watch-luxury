package nhom9.watchluxury.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.model.api.APIResource;
import nhom9.watchluxury.data.model.api.ResponseCode;
import nhom9.watchluxury.data.remote.TokenManager;
import nhom9.watchluxury.data.repo.UserRepository;

public class UserInfoViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();

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
        user = new MutableLiveData<>(null);
        status = new MutableLiveData<>(Status.NONE);

        loadUserInfo();
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

        disposables.add(
                userRepo.getUser(TokenManager.getUserId())
                        .observeOn(AndroidSchedulers.mainThread(), true)
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new UserObserver())
        );
    }

    private class UserObserver extends DisposableSubscriber<APIResource<User>> {

        @Override
        public void onNext(@NonNull APIResource<User> res) {
            user.setValue(res.getData());
            status.setValue(res.getResponseCode() == ResponseCode.SUCCESS ? Status.SUCCESS : Status.ERROR);
            Log.d("UserInfoViewModel", "onNext: " + res);
        }

        @Override
        public void onError(@NonNull Throwable e) {
//            user.setValue(null);
            status.setValue(Status.ERROR);
            Log.e("UserInfoViewModel", "onError: " + e);
        }

        @Override
        public void onComplete() {

        }
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}
