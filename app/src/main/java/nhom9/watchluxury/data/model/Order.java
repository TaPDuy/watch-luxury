package nhom9.watchluxury.data.model;

import androidx.annotation.NonNull;

import com.squareup.moshi.Json;

import java.util.Date;
import java.util.List;

import nhom9.watchluxury.util.JsonUtils;

public class Order {

    private int id;

    private User user;
    private String name;
    @Json(name = "phone_number")
    private String phoneNumber;
    private String address;
    private List<Product> products;

    private int total;
    private int status;

    @Json(name = "time_added")
    private Date timeCreated;

    public Order(int id, User user, String name, String phoneNumber, String address, List<Product> products, int total, int status, Date timeCreated) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.products = products;
        this.total = total;
        this.status = status;
        this.timeCreated = timeCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    @NonNull
    @Override
    public String toString() {
        return "Order: " + JsonUtils.toJson(this, Order.class);
    }
}
