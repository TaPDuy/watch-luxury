package nhom9.watchluxury.util;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

public class JsonUtils {

    private static Moshi moshi;

    public static <T> String toJson(T obj, Class<T> clazz) {
        if(moshi == null) {
            moshi = new Moshi.Builder().build();
        }
        JsonAdapter<T> adapter = moshi.adapter(clazz);
        return adapter.indent("\t").toJson(obj);
    }
}
