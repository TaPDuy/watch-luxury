package nhom9.watchluxury.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import nhom9.watchluxury.data.model.Category;
import nhom9.watchluxury.data.remote.model.APIResource;
import nhom9.watchluxury.data.repo.ProductRepository;

public class HomeViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final ProductRepository productRepo;
    private List<Category> categories;
    private final PublishSubject<List<Category>> subject;

    public HomeViewModel() {
        this.productRepo = new ProductRepository();
        this.categories = new ArrayList<>();
        subject = PublishSubject.create();
    }

    public PublishSubject<List<Category>> getCategories() {
        return subject;
    }

    public void loadData() {

        disposables.add(
                productRepo.getCategories()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<APIResource<List<Category>>>() {

                            @Override
                            public void onSuccess(@NonNull APIResource<List<Category>> res) {
//                                status.setValue(res.getResponseCode() == ResponseCode.SUCCESS ? Status.SUCCESS : Status.ERROR);
//                                List<Category> newCats = res.getData();


                                categories = res.getData();
                                subject.onNext(categories);
                                Log.d("HomeViewModel", "onSuccess: " + res);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
//                                status.setValue(Status.ERROR);
                                Log.e("HomeViewModel", "onError: " + e);
                            }
                        })
        );
    }
}
