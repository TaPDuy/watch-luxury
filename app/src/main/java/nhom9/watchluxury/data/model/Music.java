package nhom9.watchluxury.data.model;

public class Music {
    private String name ;
    private  int song ;
    public Music(String name , int song) {
        this.name = name;
        this.song = song ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSong() {
        return song;
    }

    public void setSong(int song) {
        this.song = song;
    }
}
