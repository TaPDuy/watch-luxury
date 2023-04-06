package nhom9.watchluxury.viewmodel.adapter;

import androidx.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;

import nhom9.watchluxury.data.model.Product;

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
