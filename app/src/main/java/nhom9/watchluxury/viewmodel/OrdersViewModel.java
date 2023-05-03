package nhom9.watchluxury.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import nhom9.watchluxury.data.local.TokenManager;
import nhom9.watchluxury.data.model.Order;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.repo.OrderRepository;

public class OrdersViewModel extends ViewModel {

    private final LiveData<String> username;
    private final MutableLiveData<List<Order>> orders;

    private final OrderRepository orderRepo;

    private final CompositeDisposable disposables;

    public OrdersViewModel() {
        this.username = new MutableLiveData<>(TokenManager.getString(TokenManager.KEY_USERNAME));
        this.orders = new MutableLiveData<>(new ArrayList<>());

        this.orderRepo = new OrderRepository();

        this.disposables = new CompositeDisposable();
    }

    public LiveData<String> getUsername() {
        return username;
    }

    public MutableLiveData<List<Order>> getOrders() {
        return orders;
    }

    public void loadOrders() {
        disposables.add(
                orderRepo.getOrders(TokenManager.getUserId())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new OrderObserver())
        );
    }

    private class OrderObserver extends DisposableSingleObserver<APIResource<List<Order>>> {

        @Override
        public void onSuccess(@NonNull APIResource<List<Order>> res) {
            orders.setValue(res.getData());
            Log.d("OrderViewModel", "onSuccess: " + res);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.e("OrderViewModel", "onError: " + e);
        }
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }
}
