package nhom9.watchluxury.data;

import android.util.Log;

import androidx.annotation.Nullable;

import java.util.HashSet;

import nhom9.watchluxury.data.local.source.LocalSource;
import nhom9.watchluxury.data.remote.source.RemoteSource;

public class DataSource {

    private final static HashSet<LocalSource> localSources = new HashSet<>();
    private final static HashSet<RemoteSource> remoteSources = new HashSet<>();

    @Nullable
    public static <T> T get(Class<T> type) {

        if (LocalSource.class.isAssignableFrom(type))
            return getLocalSource(type);

        if (RemoteSource.class.isAssignableFrom(type))
            return getRemoteSource(type);

        Log.e("DataSource", type + " must extends LocalSource or RemoteSource.");
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getLocalSource(Class<T> type) {

        for (LocalSource source : localSources) {
            if (type.isInstance(source))
                return (T) source;
        }

        T source;
        try {
            source = type.newInstance();
            localSources.add((LocalSource) source);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }

        return source;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getRemoteSource(Class<T> type) {

        for (RemoteSource source : remoteSources) {
            if (type.isInstance(source))
                return (T) source;
        }

        T source;
        try {
            source = type.newInstance();
            remoteSources.add((RemoteSource) source);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }

        return source;
    }
}
