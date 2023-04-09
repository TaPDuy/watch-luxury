package nhom9.watchluxury.event;

import androidx.annotation.NonNull;

import nhom9.watchluxury.util.JsonUtils;

public class FavoriteEvent extends Event {

    public enum Action {
        ADD,
        REMOVE
    }

    private int userID;
    private int productID;
    private Action action;

    public FavoriteEvent(int userID, int productID) {
        this.userID = userID;
        this.productID = productID;
        this.action = Action.ADD;
    }

    public FavoriteEvent(int userID, int productID, Action action) {
        this.userID = userID;
        this.productID = productID;
        this.action = action;
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

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @NonNull
    @Override
    public String toString() {
        return "FavoriteEvent: " + JsonUtils.toJson(this, FavoriteEvent.class);
    }
}
