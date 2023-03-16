package nhom9.watchluxury.data.model.api;

import androidx.annotation.NonNull;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class APIResponse<T> implements Serializable {

    @Json(name="code")
    private int code;
    @Json(name="msg")
    private String message;
    @Json(name="data")
    private T data;

    public APIResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getResponseCode() {
        return code;
    }

    public void setResponseCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @NonNull
    @Override
    public String toString() {
        return this.message + " (" + this.code + ")";
    }
}
