package nhom9.watchluxury.data.repo;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.BuildConfig;
import nhom9.watchluxury.data.remote.model.WeatherResponse;
import nhom9.watchluxury.data.remote.service.WeatherService;
import nhom9.watchluxury.util.APIUtils;

public class WeatherRepository {

    private static final WeatherService API = APIUtils.getWeatherService();
    private static final String API_KEY = BuildConfig.WEATHER_API_KEY;

    public Single<WeatherResponse> loadWeatherInfo(String cityName) {
        return API.getWeatherInfo(API_KEY, cityName);
    }
}
