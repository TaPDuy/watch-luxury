package nhom9.watchluxury.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;
import com.google.android.material.appbar.AppBarLayout;

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

        binding.ablTopBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout layout, int vOff) {
                if (scrollRange == -1) {
                    scrollRange = layout.getTotalScrollRange();
                }
                if (scrollRange + vOff == 0) {
                    binding.cblTopBar.setTitle(category.getName());
                    isShow = true;
                } else if(isShow) {
                    binding.cblTopBar.setTitle(" ");
                    isShow = false;
                }
            }
        });

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
        binding.rvProductList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        binding.topBar.setNavigationOnClickListener(view -> finish());

        initObserver();
        viewModel.loadProducts();
    }

    private void initObserver() {
        viewModel.getProducts().observe(this, adapter::setItems);
    }

    private static class ViewModelFactory implements ViewModelProvider.Factory {

        private final Category category;

        public ViewModelFactory(Category category) {
            this.category = category;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            try {
                return modelClass.getConstructor(Category.class).newInstance(category);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                     InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}