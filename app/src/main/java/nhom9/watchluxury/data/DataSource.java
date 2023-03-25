package nhom9.watchluxury.data;

import nhom9.watchluxury.data.local.UserLocalSource;
import nhom9.watchluxury.data.remote.UserRemoteSource;

public class DataSource {

    private static UserLocalSource userLocalSource;
    private static UserRemoteSource userRemoteSource;

    public static UserLocalSource getLocalUser() {
        if (userLocalSource == null)
            userLocalSource = new UserLocalSource();
        return userLocalSource;
    }

    public static UserRemoteSource getRemoteUser() {
        if (userRemoteSource == null)
            userRemoteSource = new UserRemoteSource();
        return userRemoteSource;
    }
}
