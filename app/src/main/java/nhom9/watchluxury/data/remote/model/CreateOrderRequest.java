package nhom9.watchluxury.data.remote.model;

import androidx.annotation.NonNull;

import com.squareup.moshi.Json;

import nhom9.watchluxury.util.JsonUtils;

public class CreateOrderRequest {

    @Json(name = "user")
    private int userID;

    private String name;

    @Json(name = "phone_number")
    private String phoneNumber;
    private String address;

    @Json(name = "products")
    private int[] productIDs;

    public CreateOrderRequest(int userID, int[] productIDs) {
        this(userID, null, null, null, productIDs);
    }

    public CreateOrderRequest(int userID, String name, String phoneNumber, String address, int[] productIDs) {
        this.userID = userID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.productIDs = productIDs;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public int[] getProductIDs() {
        return productIDs;
    }

    public void setProductIDs(int[] productIDs) {
        this.productIDs = productIDs;
    }

    @NonNull
    @Override
    public String toString() {
        return "CreateOrderRequest: " + JsonUtils.toJson(this, CreateOrderRequest.class);
    }
}
