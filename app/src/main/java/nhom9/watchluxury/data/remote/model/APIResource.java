package nhom9.watchluxury.data.remote.model;

import androidx.annotation.NonNull;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class APIResource<T> implements Serializable {

    @Json(name="code")
    private final int code;
    @Json(name="msg")
    private final String message;
    @Json(name="data")
    private final T data;

    public APIResource(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getResponseCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @NonNull
    @Override
    public String toString() {
        return this.message + " (" + this.code + ")";
    }
}
