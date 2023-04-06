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
import nhom9.watchluxury.data.model.Category;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.repo.ProductRepository;

public class CategoryViewModel extends ViewModel {

    private final MutableLiveData<Category> category;
    private final MutableLiveData<List<Product>> products;
    private final ProductRepository productRepo;
    private final CompositeDisposable disposables;

    public CategoryViewModel(Category category) {
        this.productRepo = new ProductRepository();
        this.disposables = new CompositeDisposable();

        this.products = new MutableLiveData<>(new ArrayList<>());
        this.category = new MutableLiveData<>(category);
    }

    public MutableLiveData<List<Product>> getProducts() {
        return products;
    }

    public LiveData<Category> getCategory() {
        return this.category;
    }

    public void loadProducts() {
        disposables.add(
                productRepo.getProductByCategory(category.getValue())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<APIResource<List<Product>>>() {
                            @Override
                            public void onSuccess(@NonNull APIResource<List<Product>> res) {
                                products.setValue(res.getData());
                                Log.d("CategoryViewModel", "onSuccess: " + res);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("CategoryViewModel", "onError: " + e);
                            }
                        })
        );
    }
}
