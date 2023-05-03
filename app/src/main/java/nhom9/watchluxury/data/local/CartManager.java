package nhom9.watchluxury.data.local;

import java.util.ArrayList;
import java.util.List;

import nhom9.watchluxury.data.model.Product;

public class CartManager {

    private static final List<Product> CART = new ArrayList<>();
    private static long total = 0;

    public static void addItem(Product product) {
        if (!hasItem(product)) {
            CART.add(product);
            total += product.getPrice();
        }
    }

    public static void removeItem(Product product) {
        boolean removed = CART.removeIf(p -> p.getId() == product.getId());
        if (removed)
            total -= product.getPrice();
    }

    public static void clear() {
        CART.clear();
        total = 0;
    }

    public static long getTotal() {
        return total;
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
