package nhom9.watchluxury.data.local.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;

import java.util.Date;

import nhom9.watchluxury.data.model.Order;
import nhom9.watchluxury.data.model.User;
import nhom9.watchluxury.util.DateUtils;

@Entity(
        tableName = "tbl_order",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = @Index(value = "user_id")
)
public class OrderRow {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "user_id")
    private int userID;

    private String name;
    @Json(name = "phone_number")
    private String phoneNumber;
    private String address;

    private long total;
    private int status;

    @Json(name = "time_added")
    private Date timeCreated;

    public OrderRow(int id, int userID, String name, String phoneNumber, String address, long total, int status, Date timeCreated) {
        this.id = id;
        this.userID = userID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.total = total;
        this.status = status;
        this.timeCreated = timeCreated;
    }

    @Ignore
    public OrderRow(Order order) {
        this(
                order.getId(),
                order.getUser().getId(),
                order.getName(),
                order.getPhoneNumber(),
                order.getAddress(),
                //        this.products = order.products;
                order.getTotal(),
                order.getStatus(),
                DateUtils.parse(order.getTimeCreated())
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }
}
