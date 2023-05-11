package nhom9.watchluxury.data.remote.model;

import java.util.List;

import nhom9.watchluxury.data.model.City;
import nhom9.watchluxury.data.model.WeatherData;

public class WeatherResponse {

    private List<WeatherData> list;
    private City city;

    public WeatherResponse(List<WeatherData> list, City city) {

        this.list = list;
        this.city = city;
    }

    public List<WeatherData> getList() {
        return list;
    }

    public void setList(List<WeatherData> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
