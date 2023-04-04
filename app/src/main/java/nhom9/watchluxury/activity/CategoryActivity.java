package nhom9.watchluxury.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.github.vivchar.rendererrecyclerviewadapter.LoadMoreViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewFinder;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

import java.lang.reflect.InvocationTargetException;

import nhom9.watchluxury.R;
import nhom9.watchluxury.data.model.Category;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.databinding.ActivityCategoryBinding;
import nhom9.watchluxury.util.APIUtils;
import nhom9.watchluxury.viewmodel.CategoryViewModel;
import nhom9.watchluxury.viewmodel.adapter.ProductAdapter;

public class CategoryActivity extends AppCompatActivity {

    private ActivityCategoryBinding binding;
    private CategoryViewModel viewModel;
    private RendererRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Category category = (Category) getIntent().getSerializableExtra("category");
        viewModel = new ViewModelProvider(this, new ViewModelFactory(category)).get(CategoryViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        adapter = new ProductAdapter();
        adapter.registerRenderer(new ViewRenderer<>(
                R.layout.item_product_small,
                Product.class,
                (model, finder, payloads) -> {
                    finder.setText(R.id.tv_itemLabel, model.getName());
                    finder.setText(R.id.tv_itemPrice, String.format("%,d", model.getPrice()) + "đ");
                    APIUtils.loadImage(model.getImagePath(), finder.find(R.id.img_itemThumbnail));
                    finder.setOnClickListener(() -> {
                        Intent i3 = new Intent(this, ProductInfoActivity.class);
                        i3.putExtra("productID", model.getId());
                        startActivity(i3);
                    });
                })
        );

        binding.rvProductList.setAdapter(adapter);
        binding.rvProductList.setLayoutManager(new GridLayoutManager(this, 2));

        binding.topBar.setNavigationOnClickListener(view -> finish());

        initObserver();
        viewModel.loadProducts();
    }

    private void initObserver() {
        viewModel.getProducts().observe(this, products -> {
            adapter.setItems(products);
        });
    }

    private class ViewModelFactory implements ViewModelProvider.Factory {

        private final Category category;

        public ViewModelFactory(Category category) {
            this.category = category;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            try {
                return (T) modelClass.getConstructor(Category.class).newInstance(category);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                     InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}