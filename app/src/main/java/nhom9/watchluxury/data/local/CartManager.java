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
        CART.removeIf(p -> p.getId() == product.getId());
    }

    public static boolean hasItem(int productID) {
        for (Product product : CART)
            if (product.getId() == productID)
                return true;

        return false;
    }

    public static boolean hasItem(Product product) {
        return hasItem(product.getId());
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
