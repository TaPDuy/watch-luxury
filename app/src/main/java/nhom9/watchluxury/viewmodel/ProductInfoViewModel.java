package nhom9.watchluxury.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import nhom9.watchluxury.data.local.CartManager;
import nhom9.watchluxury.data.local.TokenManager;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.repo.ProductRepository;
import nhom9.watchluxury.event.CartEventBus;
import nhom9.watchluxury.event.FavoriteEventBus;

public class ProductInfoViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final CartEventBus cartEventBus;
    private final MutableLiveData<Product> product;
    private final MutableLiveData<String> imageUrl;
    private final MutableLiveData<Boolean> isFavorited;
    private boolean initialFavorite = false;
    private final ProductRepository productRepo;
    private final int id;
    private final MutableLiveData<Boolean> isInCart;

    public ProductInfoViewModel(Integer productID) {
        this.productRepo = new ProductRepository();
        this.product = new MutableLiveData<>(null);
        this.imageUrl = new MutableLiveData<>("");
        this.isFavorited = new MutableLiveData<>(false);
        this.id = productID;

        boolean inCart = CartManager.hasItem(productID);
        this.isInCart = new MutableLiveData<>(inCart);

        cartEventBus = CartEventBus.getInstance();

        loadProductInfo();
    }

    public MutableLiveData<Boolean> isInCart() {
        return isInCart;
    }

    public MutableLiveData<Product> getProduct() {
        return this.product;
    }

    public MutableLiveData<String> getImageUrl() {
        return this.imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setIsFavorited(boolean b) {
        this.isFavorited.setValue(b);
    }

    public MutableLiveData<Boolean> getIsFavorited() {
        return isFavorited;
    }

    private void loadProductInfo() {

        disposables.add(
                productRepo.getProduct(this.id)
                        .observeOn(AndroidSchedulers.mainThread(), true)
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new ProductObserver())
        );
    }

    private void loadFavorite() {
        disposables.add(
                productRepo.isFavorited(TokenManager.getUserId(), id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<Boolean>() {

                            @Override
                            public void onSuccess(@NonNull Boolean res) {
                                isFavorited.setValue(res);
                                initialFavorite = res;
                                Log.d("ProductInfoViewModel", "onNext: " + isFavorited);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d("ProductInfoViewModel", "onError: " + e);
                            }
                        })
        );
    }

    private class ProductObserver extends DisposableSubscriber<APIResource<Product>> {

        @Override
        public void onNext(APIResource<Product> res) {
            Product p = res.getData();
            product.setValue(p);
            imageUrl.setValue(p.getImagePath());

            boolean inCart = CartManager.hasItem(p.getId());
            isInCart.setValue(inCart);

            loadFavorite();
            Log.d("ProductInfoViewModel", "onNext: " + res);
        }

        @Override
        public void onError(Throwable t) {
            Log.d("ProductInfoViewModel", "onError: " + t);
        }

        @Override
        public void onComplete() {

        }
    }

    public void onCartClicked(boolean checked) {

        isInCart.setValue(checked);

        Product p = product.getValue();
        if (p != null) {
            if (checked) {
                CartManager.addItem(p);
                cartEventBus.addToCart(p);
            } else {
                CartManager.removeItem(p);
                cartEventBus.removeFromCart(p);
            }
        }
    }

    @Override
    protected void onCleared() {

        if (initialFavorite != Boolean.TRUE.equals(isFavorited.getValue())) {

            FavoriteEventBus eventBus = FavoriteEventBus.getInstance();
            if (Boolean.TRUE.equals(isFavorited.getValue()))
                eventBus.addFavorite(TokenManager.getUserId(), id);
            else
                eventBus.removeFavorite(TokenManager.getUserId(), id);
        }

        disposables.clear();
        super.onCleared();
    }
}
