package nhom9.watchluxury.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import java.lang.reflect.InvocationTargetException;

import nhom9.watchluxury.R;
import nhom9.watchluxury.databinding.ActivityProductInfoBinding;
import nhom9.watchluxury.util.APIUtils;
import nhom9.watchluxury.viewmodel.ProductInfoViewModel;

public class ProductInfoActivity extends AppCompatActivity {

    private ActivityProductInfoBinding binding;
    private ProductInfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getIntent().getIntExtra("productID", 0);

        viewModel = new ViewModelProvider(this, new ViewModelFactory(id)).get(ProductInfoViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_info);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        initObserver();

        binding.topBar.setNavigationOnClickListener(view -> finish());
        binding.topBar.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.favorite) {
                // Implement favorite
                return true;
            }
            return false;
        });
    }

    private void initObserver() {
        viewModel.getImageUrl().observe(this, url -> APIUtils.loadImage(url, binding.imgProduct));
    }

    private static class ViewModelFactory implements ViewModelProvider.Factory {

        private final int id;

        public ViewModelFactory(int id) {
            this.id = id;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            try {
                return modelClass.getConstructor(Integer.class).newInstance(id);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                     InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}