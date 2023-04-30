package nhom9.watchluxury.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

import io.reactivex.rxjava3.disposables.Disposable;
import nhom9.watchluxury.R;
import nhom9.watchluxury.activity.CategoryActivity;
import nhom9.watchluxury.activity.ProductInfoActivity;
import nhom9.watchluxury.data.model.Category;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.databinding.FragmentHomeBinding;
import nhom9.watchluxury.databinding.ItemCategoryBinding;
import nhom9.watchluxury.util.APIUtils;
import nhom9.watchluxury.viewmodel.HomeViewModel;
import nhom9.watchluxury.viewmodel.adapter.ProductAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private Disposable disposable;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(int page, String title) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(requireActivity());
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObserver();
        viewModel.loadData();
    }

    private void initObserver() {
        disposable = viewModel.getCategories().subscribe(categories -> {
            for (Category cats : categories)
                if (cats.getProducts().size() > 0)
                    addCategory(cats);
        });
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }

    private void addCategory(Category category) {

        ItemCategoryBinding itemBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.item_category,
                binding.llCategories,
                true
        );

        itemBinding.tvCategoryName.setText(category.getName());
        itemBinding.tvSeeMore.setOnClickListener(view -> {
            Intent i = new Intent(this.getContext(), CategoryActivity.class);
            i.putExtra("category", category);
            startActivity(i);
        });

        ProductAdapter productAdapter = new ProductAdapter();
        productAdapter.registerRenderer(new ViewRenderer<>(R.layout.item_product,
                Product.class,
                (model, finder, payloads) -> {
                    finder.setText(R.id.tv_itemLabel, model.getName());
                    finder.setText(R.id.tv_itemPrice, String.format("%,d", model.getPrice()) + "đ");
                    APIUtils.loadImage(model.getImagePath(), finder.find(R.id.img_itemThumbnail));
                    finder.setOnClickListener(() -> {
                        Intent i3 = new Intent(this.getContext(), ProductInfoActivity.class);
                        i3.putExtra("productID", model.getId());
                        startActivity(i3);
                    });
                })
        );
        productAdapter.setItems(category.getProducts());

        itemBinding.rvProductList.setAdapter(productAdapter);
        itemBinding.rvProductList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }
}