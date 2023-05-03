package nhom9.watchluxury.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import nhom9.watchluxury.data.local.TokenManager;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.repo.UserRepository;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Boolean> finishLoading;

    private UserRepository userRepo;

    private CompositeDisposable disposables;

    public MainViewModel() {
        finishLoading = new MutableLiveData<>(false);
        disposables = new CompositeDisposable();

        userRepo = new UserRepository();
    }

    public MutableLiveData<Boolean> getLoading() {
        return finishLoading;
    }

    public void loadData() {
        disposables.add(
            userRepo.getUser(TokenManager.getUserId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableSubscriber<APIResource<User>>() {
                        @Override
                        public void onNext(@NonNull APIResource<User> res) {
                            TokenManager.saveUser(res.getData());
                            Log.i("Init", "onNext: " + res);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.e("Init", "onError: " + e);
                        }

                        @Override
                        public void onComplete() {
                            finishLoading.setValue(true);
                        }
                    })
        );
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}
