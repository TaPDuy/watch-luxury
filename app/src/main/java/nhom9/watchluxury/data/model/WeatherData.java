package nhom9.watchluxury.data.model;

import java.util.List;

public class WeatherData {

    private int dt;

    private Temperature main;

    private List<WeatherType> weather = null;

    public WeatherData(int dt, Temperature main, List<WeatherType> weather) {
        this.dt = dt;
        this.main = main;
        this.weather = weather;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Temperature getMain() {
        return main;
    }

    public void setMain(Temperature main) {
        this.main = main;
    }

    public List<WeatherType> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherType> weather) {
        this.weather = weather;
    }

}
