package nhom9.watchluxury.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import nhom9.watchluxury.databinding.FragmentFavoriteBinding;
import nhom9.watchluxury.viewmodel.HomeViewModel;

public class FavoriteFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentFavoriteBinding binding;

    public FavoriteFragment() {
    }

    public static FavoriteFragment newInstance(int page, String title) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void setViewModel(HomeViewModel viewModel) {
        this.viewModel = viewModel;
    }
}