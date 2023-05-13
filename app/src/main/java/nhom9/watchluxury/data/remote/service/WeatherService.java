package nhom9.watchluxury.data.remote.service;

import io.reactivex.rxjava3.core.Single;
import nhom9.watchluxury.data.remote.model.WeatherResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("forecast/")
    Single<WeatherResponse> getWeatherInfo(@Query("appid") String appID, @Query(value = "q") String cityName);
}
