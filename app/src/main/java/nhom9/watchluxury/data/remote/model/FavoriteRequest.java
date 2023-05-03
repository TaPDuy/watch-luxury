package nhom9.watchluxury.data.remote.model;

import androidx.annotation.NonNull;

import com.squareup.moshi.Json;

import nhom9.watchluxury.util.JsonUtils;

public class FavoriteRequest {

    @Json(name = "user")
    private int userID;
    @Json(name = "product")
    private int productID;

    public FavoriteRequest(int userID, int productID) {
        this.userID = userID;
        this.productID = productID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    @NonNull
    @Override
    public String toString() {
        return "FavoriteRequest: " + JsonUtils.toJson(this, FavoriteRequest.class);
    }
}
