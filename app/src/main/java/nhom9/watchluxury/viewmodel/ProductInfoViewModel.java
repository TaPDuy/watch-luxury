package nhom9.watchluxury.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.repo.ProductRepository;

public class ProductInfoViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Product> product;
    private final MutableLiveData<String> imageUrl;
    private final ProductRepository productRepo;
    private final int id;

    public ProductInfoViewModel(Integer productID) {
        this.productRepo = new ProductRepository();
        this.product = new MutableLiveData<>(null);
        this.imageUrl = new MutableLiveData<>("");
        this.id = productID;

        loadProductInfo();
    }

    public MutableLiveData<Product> getProduct() {
        return this.product;
    }

    public MutableLiveData<String> getImageUrl() {
        return this.imageUrl;
    }

    private void loadProductInfo() {

        disposables.add(
                productRepo.getProduct(this.id)
                        .observeOn(AndroidSchedulers.mainThread(), true)
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new ProductObserver())
        );
    }

    private class ProductObserver extends DisposableSubscriber<APIResource<Product>> {

        @Override
        public void onNext(APIResource<Product> res) {
            product.setValue(res.getData());
            imageUrl.setValue(res.getData().getImagePath());
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

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}
