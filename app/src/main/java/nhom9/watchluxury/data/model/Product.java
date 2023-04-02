package nhom9.watchluxury.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;
import com.squareup.moshi.Json;

import java.io.Serializable;

import nhom9.watchluxury.util.JsonUtils;

@Entity(tableName = "tbl_product")
public class Product implements Serializable, ViewModel {

    @PrimaryKey
    private int id;
    private String name;
    private String description;
    private String brand;
    @Json(name="image")
    private String imagePath;
    private int price;

    public Product(int id, String name, String description, String brand, String imagePath, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.imagePath = imagePath;
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @NonNull
    @Override
    public String toString() {
        return "Product: " + JsonUtils.toJson(this, Product.class);
    }
}
