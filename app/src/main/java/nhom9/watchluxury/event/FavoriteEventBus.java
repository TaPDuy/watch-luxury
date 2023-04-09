package nhom9.watchluxury.event;

public class FavoriteEventBus extends EventBus<FavoriteEvent> {

    private static FavoriteEventBus instance;

    public static FavoriteEventBus getInstance() {
        if (instance == null)
            instance = new FavoriteEventBus();

        return instance;
    }

    public void addFavorite(int userID, int productID) {
        invoke(new FavoriteEvent(userID, productID, FavoriteEvent.Action.ADD));
    }

    public void removeFavorite(int userID, int productID) {
        invoke(new FavoriteEvent(userID, productID, FavoriteEvent.Action.REMOVE));
    }
}
