package nhom9.watchluxury.viewmodel.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewFinder;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

import java.util.List;

import nhom9.watchluxury.R;
import nhom9.watchluxury.data.model.Category;

public class CategoryAdapter extends RendererRecyclerViewAdapter {

    public CategoryAdapter() {
        super();

        registerRenderer(new CategoryViewRenderer());
        setDiffCallback(new DiffCallback());
    }

    private static class CategoryViewRenderer extends CompositeViewRenderer<Category, ViewFinder> {

        public CategoryViewRenderer() {
            super(
                    R.layout.item_category,
                    R.id.rv_productList,
                    Category.class,
                    (model, finder, payloads) -> finder.setText(R.id.tv_categoryName, model.getName())
            );
        }

        @Override
        protected void bindAdapterItems(@NonNull RendererRecyclerViewAdapter adapter, @NonNull List<? extends ViewModel> models) {
            adapter.setItems(models);
        }

        @NonNull
        @Override
        protected RecyclerView.LayoutManager createLayoutManager() {
            return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        }

        @NonNull
        @Override
        protected RendererRecyclerViewAdapter createAdapter() {
            return new ProductAdapter();
        }
    }

    private static class DiffCallback extends DefaultDiffCallback<Category> {
        @Override
        public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }
}
