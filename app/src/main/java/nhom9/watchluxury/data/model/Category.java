package nhom9.watchluxury.data.model;

import androidx.annotation.NonNull;

import com.github.vivchar.rendererrecyclerviewadapter.CompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable, CompositeViewModel {

    private int id;
    private String name;
    private String slug;
    private String description;

    private List<Product> products;

    public Category(int id, String name, String slug, String description, List<Product> products) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public String toString() {
        return this.slug;
    }

    @NonNull
    @Override
    public List<? extends ViewModel> getItems() {
        if (this.products == null)
            this.products = new ArrayList<>();
        return this.products;
    }
}
