package nhom9.watchluxury.data.remote.source;

import nhom9.watchluxury.data.local.TokenManager;

public abstract class RemoteSource {

    protected String token() {
        return "Bearer " + TokenManager.getAccessToken();
    }
}
