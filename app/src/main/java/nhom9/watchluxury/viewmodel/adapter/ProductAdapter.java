package nhom9.watchluxury.viewmodel.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback;
import com.github.vivchar.rendererrecyclerviewadapter.OnClickListener;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewFinder;
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

import nhom9.watchluxury.R;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.util.APIUtils;

public class ProductAdapter extends RendererRecyclerViewAdapter {

    public ProductAdapter() {
        super();

        setDiffCallback(new DiffCallback());
    }

    private static class DiffCallback extends DefaultDiffCallback<Product> {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }

}
