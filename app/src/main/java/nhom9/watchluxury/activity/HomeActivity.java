package nhom9.watchluxury.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

import io.reactivex.rxjava3.disposables.Disposable;
import nhom9.watchluxury.R;
import nhom9.watchluxury.data.local.TokenManager;
import nhom9.watchluxury.data.model.Category;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.databinding.ActivityHomePageBinding;
import nhom9.watchluxury.databinding.ItemCategoryBinding;
import nhom9.watchluxury.util.APIUtils;
import nhom9.watchluxury.viewmodel.HomeViewModel;
import nhom9.watchluxury.viewmodel.adapter.ProductAdapter;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomePageBinding binding;
    private HomeViewModel viewModel;
    private Disposable disposable;

    private boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_page);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        initObserver();

        binding.floatingBtn.setOnClickListener(view -> {
            if (check) {
//                    send.show();
//                    lucky.show();
                binding.floatingHome.show();
                binding.floatingFavorite.show();
                binding.floatingCart.show();
                check = false;
            } else {
                binding.floatingHome.hide();
//                    send.hide();
//                    lucky.hide();
                binding.floatingFavorite.hide();
                binding.floatingCart.hide();
                check = true;
            }
        });

        binding.topBar.setNavigationOnClickListener(view -> binding.sidebarLayout.open());
        binding.topBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.search) {
                // search
                return true;
            }
            return false;
        });

        binding.sidebar.setNavigationItemSelectedListener(item -> {
            boolean res = true;
            switch (item.getItemId()) {
                case R.id.logout:
                    TokenManager.deleteTokens();
                    finish();
                    break;
                case R.id.account:
                    Intent i1 = new Intent(this, UserInfoActivity.class);
                    startActivity(i1);
                    break;
                case R.id.about:
                    Intent i2 = new Intent(this, AboutActivity.class);
                    startActivity(i2);
                    break;
                case R.id.setting:

                    break;
                default:
                    res = false;
                    break;
            }
            binding.sidebarLayout.closeDrawer(GravityCompat.START);
            return res;
        });

        viewModel.loadData();
    }

    private void addCategory(Category category) {

        ItemCategoryBinding itemBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.item_category,
                binding.llCategories,
                true
        );

        itemBinding.tvCategoryName.setText(category.getName());

        ProductAdapter productAdapter = new ProductAdapter();
        productAdapter.registerRenderer(new ViewRenderer<>(R.layout.item_product,
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
        productAdapter.setItems(category.getProducts());

        itemBinding.rvProductList.setAdapter(productAdapter);
        itemBinding.rvProductList.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }

    private void initObserver() {
        disposable = viewModel.getCategories().subscribe(categories -> {
            for (Category cats : categories)
                if (cats.getProducts().size() > 0)
                    addCategory(cats);
        });
    }
}