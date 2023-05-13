package nhom9.watchluxury.data.model;

import io.realm.RealmObject;

public class Note extends RealmObject {

    String title;
    String description;
    long createdTime;

    public Note() {
    }

    public Note(String title, String description, long createdTime) {
        this.title = title;
        this.description = description;
        this.createdTime = createdTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}