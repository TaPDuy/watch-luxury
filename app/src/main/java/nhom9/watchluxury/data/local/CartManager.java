package nhom9.watchluxury.data.local;

import java.util.ArrayList;
import java.util.List;

import nhom9.watchluxury.data.model.Product;

public class CartManager {

    private static final List<Product> CART = new ArrayList<>();

    public static void addItem(Product product) {
        if (!hasItem(product)) {
            CART.add(product);
        }
    }

    public static void removeItem(Product product) {
        if (hasItem(product)) {
            CART.remove(product);
        }
    }

    public static boolean hasItem(Product product) {
        return CART.contains(product);
    }

    public static List<Product> getCart() {
        return CART;
    }

    public static boolean isEmpty() {
        return CART.isEmpty();
    }

    public static int getSize() {
        return CART.size();
    }
}
