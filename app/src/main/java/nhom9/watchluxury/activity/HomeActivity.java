package nhom9.watchluxury.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.ferfalk.simplesearchview.SimpleSearchView;

import java.util.ArrayList;
import java.util.List;

import nhom9.watchluxury.R;
import nhom9.watchluxury.activity.fragment.CartFragment;
import nhom9.watchluxury.activity.fragment.FavoriteFragment;
import nhom9.watchluxury.activity.fragment.HomeFragment;
import nhom9.watchluxury.data.local.TokenManager;
import nhom9.watchluxury.databinding.ActivityHomePageBinding;
import nhom9.watchluxury.viewmodel.HomeViewModel;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomePageBinding binding;
    private HomeViewModel viewModel;

    private boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_page);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        // Initializing views
        initTopBar();
        initSideBar();
        initFloatingButtons();
        initContent();
    }

    private void initContent() {
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager(), getLifecycle());

        adapter.addFragment(HomeFragment.newInstance(0, "Home"));
        adapter.addFragment(FavoriteFragment.newInstance(1, "Favorites"));
        adapter.addFragment(CartFragment.newInstance(2, "Cart"));

        binding.vpContent.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.vpContent.setUserInputEnabled(false);
        binding.vpContent.setOffscreenPageLimit(2);
        binding.vpContent.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
//                binding.imgLogo.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
                binding.ablTopBar.setExpanded(position == 0);
                super.onPageSelected(position);
            }
        });
        binding.vpContent.setAdapter(adapter);
    }

    private void initSideBar() {

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
                    Intent i3 = new Intent(this, SettingsActivity.class);
                    startActivity(i3);
                    break;
                default:
                    res = false;
                    break;
            }
            binding.sidebarLayout.closeDrawer(GravityCompat.START);
            return res;
        });
    }

    private void initFloatingButtons() {

        binding.floatingBtn.setOnClickListener(view -> {

            if (binding.svSearchView.isSearchOpen()) {
                binding.svSearchView.closeSearch();
            }

            if (check) {
//                    send.show();
//                    lucky.show();
                binding.floatingBtnHome.show();
                binding.floatingBtnFavorite.show();
                binding.floatingBtnCart.show();
                check = false;
            } else {
                binding.floatingBtnHome.hide();
//                    send.hide();
//                    lucky.hide();
                binding.floatingBtnFavorite.hide();
                binding.floatingBtnCart.hide();
                check = true;
            }
        });

        binding.floatingBtnHome.setOnClickListener(view -> binding.vpContent.setCurrentItem(0));
        binding.floatingBtnFavorite.setOnClickListener(view -> binding.vpContent.setCurrentItem(1));
        binding.floatingBtnCart.setOnClickListener(view -> binding.vpContent.setCurrentItem(2));
    }

    private void initTopBar() {

        binding.topBar.setNavigationOnClickListener(view -> binding.sidebarLayout.open());
        binding.topBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.search) {
                binding.svSearchView.showSearch();
                return true;
            }
            return false;
        });

        binding.svSearchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(@NonNull String s) {
                Intent i = new Intent(HomeActivity.this, SearchResultActivity.class);
                i.putExtra("keyword", s);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(@NonNull String s) {
                return false;
            }

            @Override
            public boolean onQueryTextCleared() {
                return false;
            }
        });
        binding.svSearchView.setOnSearchViewListener(new SimpleSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                binding.btnFloatingGroup.setVisibility(View.GONE);
                binding.ablTopBar.setExpanded(false);
            }

            @Override
            public void onSearchViewClosed() {
                binding.btnFloatingGroup.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewShownAnimation() {

            }

            @Override
            public void onSearchViewClosedAnimation() {

            }
        });
    }

    private static class ViewPageAdapter extends FragmentStateAdapter {

        private final List<Fragment> fragments = new ArrayList<>();

        public ViewPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        public void addFragment(Fragment fragment) {
            fragments.add(fragment);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }
    }
}