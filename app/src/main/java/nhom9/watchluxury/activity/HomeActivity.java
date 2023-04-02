package nhom9.watchluxury.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import io.reactivex.rxjava3.disposables.Disposable;
import nhom9.watchluxury.R;
import nhom9.watchluxury.data.local.TokenManager;
import nhom9.watchluxury.databinding.ActivityHomePageBinding;
import nhom9.watchluxury.viewmodel.HomeViewModel;
import nhom9.watchluxury.viewmodel.adapter.CategoryAdapter;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomePageBinding binding;
    private HomeViewModel viewModel;
    private CategoryAdapter adapter;
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

        adapter = new CategoryAdapter();
        binding.rvCategoryList.setAdapter(adapter);
        binding.rvCategoryList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        initObserver();

        binding.floatingBtn.setOnClickListener(view -> {
            if(check){
//                    send.show();
//                    lucky.show();
                binding.floatingHome.show();
                binding.floatingFavorite.show();
                binding.floatingCart.show();
                check=false;
            }else{
                binding.floatingHome.hide();
//                    send.hide();
//                    lucky.hide();
                binding.floatingFavorite.hide();
                binding.floatingCart.hide();
                check=true;
            }
        });

        binding.topBar.setNavigationOnClickListener(view -> binding.sidebarLayout.open());
        binding.topBar.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.search) {
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
                    Intent i3 = new Intent(this, ProductInfoActivity.class);
                    i3.putExtra("productID", 3);
                    startActivity(i3);
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

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }

    private void initObserver() {
        disposable = viewModel.getCategories().subscribe(adapter::setItems);
    }
}