package nhom9.watchluxury.data.local.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {
        "order_id",
        "product_id"
})
public class OrderItem {

    @ColumnInfo(name = "order_id")
    private int orderID;
    @ColumnInfo(name = "product_id")
    private int productID;

    public OrderItem(int orderID, int productID) {
        this.orderID = orderID;
        this.productID = productID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}
