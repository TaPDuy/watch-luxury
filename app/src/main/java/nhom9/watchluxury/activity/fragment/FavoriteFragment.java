package nhom9.watchluxury.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

import io.reactivex.rxjava3.disposables.Disposable;
import nhom9.watchluxury.R;
import nhom9.watchluxury.activity.ProductInfoActivity;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.databinding.FragmentFavoriteBinding;
import nhom9.watchluxury.event.FavoriteEventBus;
import nhom9.watchluxury.util.APIUtils;
import nhom9.watchluxury.viewmodel.HomeViewModel;
import nhom9.watchluxury.viewmodel.adapter.ProductAdapter;

public class FavoriteFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentFavoriteBinding binding;
    private ProductAdapter adapter;

    private Disposable disposable;

    public FavoriteFragment() {
    }

    public static FavoriteFragment newInstance(int page, String title) {
        Log.d("FavoriteFragment", "init");
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(requireActivity());
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ProductAdapter();
        adapter.enableDiffUtil();
        adapter.registerRenderer(new ViewRenderer<>(
                R.layout.item_product_small,
                Product.class,
                (model, finder, payloads) -> {
                    finder.setText(R.id.tv_itemLabel, model.getName());
                    finder.setText(R.id.tv_itemPrice, String.format("%,d", model.getPrice()) + "đ");
                    APIUtils.loadImage(model.getImagePath(), finder.find(R.id.img_itemThumbnail));
                    finder.setOnClickListener(() -> {
                        Intent i3 = new Intent(getContext(), ProductInfoActivity.class);
                        i3.putExtra("productID", model.getId());
                        startActivity(i3);
                    });
                })
        );

        binding.rvFavoriteList.setAdapter(adapter);
        binding.rvFavoriteList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));

        initObserver();
        viewModel.loadFavorites();
    }

    private void initObserver() {
        disposable = FavoriteEventBus.getInstance().getEvents().subscribe(viewModel::onFavoriteEvent);
        viewModel.getFavorites().observe(getViewLifecycleOwner(), adapter::setItems);
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}