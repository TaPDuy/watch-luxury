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
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.repo.ProductRepository;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<String> desc;
    private final MutableLiveData<List<Product>> results;
    private final ProductRepository productRepo;
    private final CompositeDisposable disposables;

    public SearchViewModel() {
        this.productRepo = new ProductRepository();
        this.disposables = new CompositeDisposable();

        this.results = new MutableLiveData<>(new ArrayList<>());
        this.desc = new MutableLiveData<>("");
    }

    public LiveData<List<Product>> getResults() {
        return this.results;
    }

    public LiveData<String> getDescription() {
        return this.desc;
    }

    public void loadResults(String keyword) {
        disposables.add(
                productRepo.searchProducts(keyword)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<APIResource<List<Product>>>() {
                            @Override
                            public void onSuccess(@NonNull APIResource<List<Product>> res) {
                                List<Product> products = res.getData();
                                results.setValue(products);
                                desc.setValue("Found " + products.size() + " product" + (products.size() <= 1 ? "" : "s"));
                                Log.d("SearchViewModel", "onSuccess: " + res);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("SearchViewModel", "onError: " + e);
                            }
                        })
        );
    }
}
