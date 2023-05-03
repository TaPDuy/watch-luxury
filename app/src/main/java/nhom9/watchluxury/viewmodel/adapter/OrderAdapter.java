package nhom9.watchluxury.viewmodel.adapter;

import androidx.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;

import nhom9.watchluxury.data.model.Order;

public class OrderAdapter extends RendererRecyclerViewAdapter {

    public OrderAdapter() {
        super();

        setDiffCallback(new DiffCallback());
    }

    private static class DiffCallback extends DefaultDiffCallback<Order> {
        @Override
        public boolean areItemsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }

}
