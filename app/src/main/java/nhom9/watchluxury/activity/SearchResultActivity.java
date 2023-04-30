package nhom9.watchluxury.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;
import com.google.android.material.appbar.AppBarLayout;

import nhom9.watchluxury.R;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.databinding.ActivitySearchResultBinding;
import nhom9.watchluxury.util.APIUtils;
import nhom9.watchluxury.viewmodel.SearchViewModel;
import nhom9.watchluxury.viewmodel.adapter.ProductAdapter;

public class SearchResultActivity extends AppCompatActivity {

    private ActivitySearchResultBinding binding;
    private SearchViewModel viewModel;
    private RendererRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_result);
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
                    binding.cblTopBar.setTitle("Search results");
                    isShow = true;
                } else if (isShow) {
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
        viewModel.loadResults(getIntent().getStringExtra("keyword"));
    }

    private void initObserver() {
        viewModel.getResults().observe(this, adapter::setItems);
    }
}