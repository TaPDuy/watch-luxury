package nhom9.watchluxury.data.model.api;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class ChangePasswordRequest implements Serializable {

    @Json(name="old_password")
    private String oldPassword;
    @Json(name="new_password")
    private String newPassword;

    public ChangePasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
