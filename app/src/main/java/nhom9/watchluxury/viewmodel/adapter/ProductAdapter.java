package nhom9.watchluxury.viewmodel.adapter;

import androidx.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.DefaultDiffCallback;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;

import nhom9.watchluxury.R;
import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.util.APIUtils;

public class ProductAdapter extends RendererRecyclerViewAdapter {


    public ProductAdapter() {
        super();

        registerRenderer(new ViewRenderer<>(
                R.layout.item_product,
                Product.class,
                (model, finder, payloads) -> {
                    finder.setText(R.id.tv_itemLabel, model.getName());
                    APIUtils.loadImage(model.getImagePath(), finder.find(R.id.img_itemThumbnail));
                })
        );
        setDiffCallback(new DiffCallback());
    }

    private static class DiffCallback extends DefaultDiffCallback<Product> {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }

}
