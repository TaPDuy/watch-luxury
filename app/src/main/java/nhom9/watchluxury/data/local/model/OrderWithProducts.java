package nhom9.watchluxury.data.local.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import nhom9.watchluxury.data.model.Product;

public class OrderWithProducts {

    @Embedded
    OrderRow order;

    @Relation(
            entity = Process.class,
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(
                    value = OrderItem.class,
                    parentColumn = "order_id",
                    entityColumn = "product_id"
            )
    )
    List<Product> products;
}
