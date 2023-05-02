package nhom9.watchluxury.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;
import nhom9.watchluxury.data.local.CartManager;
import nhom9.watchluxury.data.local.TokenManager;
import nhom9.watchluxury.data.model.Category;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.remote.model.FavoriteRequest;
import nhom9.watchluxury.data.repo.ProductRepository;
import nhom9.watchluxury.data.repo.UserRepository;
import nhom9.watchluxury.event.CartEvent;
import nhom9.watchluxury.event.CartEventBus;
import nhom9.watchluxury.event.FavoriteEvent;

public class HomeViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private List<Category> categories;
    private final MutableLiveData<List<Product>> favorites;
    private final MutableLiveData<List<Product>> cartItems;
    private final PublishSubject<List<Category>> subject;

    public HomeViewModel() {
        this.userRepo = new UserRepository();
        this.productRepo = new ProductRepository();
        this.categories = new ArrayList<>();
        this.favorites = new MutableLiveData<>(new ArrayList<>());
        this.cartItems = new MutableLiveData<>(new ArrayList<>());
        subject = PublishSubject.create();
    }

    public PublishSubject<List<Category>> getCategories() {
        return subject;
    }

    public MutableLiveData<List<Product>> getFavorites() {
        return favorites;
    }

    public MutableLiveData<List<Product>> getCartItems() {
        return cartItems;
    }

    public void loadData() {

        disposables.add(
                productRepo.getCategories()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<APIResource<List<Category>>>() {

                            @Override
                            public void onSuccess(@NonNull APIResource<List<Category>> res) {
                                categories = res.getData();
                                subject.onNext(categories);
                                Log.d("HomeViewModel", "onSuccess: " + res);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("HomeViewModel", "onError: " + e);
                            }
                        })
        );
    }

    public void loadFavorites() {
        disposables.add(
                userRepo.getFavorites(TokenManager.getUserId())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<APIResource<List<Product>>>() {

                            @Override
                            public void onSuccess(@NonNull APIResource<List<Product>> res) {
                                favorites.setValue(res.getData());
                                Log.d("HomeViewModel", "onSuccess: " + res);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("HomeViewModel", "onError: " + e);
                            }
                        })
        );
    }

    public void onFavoriteEvent(FavoriteEvent e) {
        disposables.add(
                e.getAction() == FavoriteEvent.Action.ADD ?
                        productRepo.addFavorite(e.getUserID(), e.getProductID())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribeWith(new ResourceSubscriber<APIResource<FavoriteRequest>>() {

                                    @Override
                                    public void onNext(@NonNull APIResource<FavoriteRequest> res) {
                                        Log.d("HomeViewModel", "Added");
                                        loadFavorites();
                                        Log.d("HomeViewModel", "onNext: " + res);
                                    }

                                    @Override
                                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                        Log.d("HomeViewModel", "onError: " + e);
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                }) :
                        productRepo.removeFavorite(e.getUserID(), e.getProductID())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribeWith(new ResourceSubscriber<APIResource<FavoriteRequest>>() {

                                    @Override
                                    public void onNext(APIResource<FavoriteRequest> res) {
                                        Log.d("HomeViewModel", "Removed");
                                        loadFavorites();
                                        Log.d("HomeViewModel", "onNext: " + res);
                                    }

                                    @Override
                                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                        Log.d("HomeViewModel", "onError: " + e);
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                })
        );
    }

    public void removeFromCart(int productID) {

        Optional<Product> product = cartItems.getValue().stream()
                .filter(p -> p.getId() == productID)
                .findFirst();

        if (product.isPresent()) {
            CartManager.removeItem(product.get());
            CartEventBus.getInstance().removeFromCart(product.get());
        }
    }

    public void onCartEvent(CartEvent e) {
        cartItems.setValue(CartManager.getCart());
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }
}
