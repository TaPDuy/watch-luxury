package nhom9.watchluxury.event;

import nhom9.watchluxury.data.model.Product;

public class CartEventBus extends EventBus<CartEvent> {

    private static CartEventBus instance;

    public static CartEventBus getInstance() {
        if (instance == null)
            instance = new CartEventBus();
        return instance;
    }

    public void addToCart(Product product) {
        invoke(new CartEvent(product));
    }

    public void removeFromCart(Product product) {
        invoke(new CartEvent(product, CartEvent.Action.REMOVE));
    }
}
