package nhom9.watchluxury.event;

import androidx.annotation.NonNull;

import nhom9.watchluxury.data.model.Product;
import nhom9.watchluxury.util.JsonUtils;

public class CartEvent extends Event {

    public enum Action {
        ADD,
        REMOVE
    }

    private Product product;
    private Action action;

    public CartEvent(Product product) {
        this.product = product;
        this.action = Action.ADD;
    }

    public CartEvent(Product product, Action action) {
        this.product = product;
        this.action = action;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @NonNull
    @Override
    public String toString() {
        return "CartEvent: " + JsonUtils.toJson(this, CartEvent.class);
    }
}
