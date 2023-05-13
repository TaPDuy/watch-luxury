package nhom9.watchluxury.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import nhom9.watchluxury.data.remote.model.WeatherResponse;
import nhom9.watchluxury.data.repo.WeatherRepository;

public class WeatherViewModel extends ViewModel {

    private final MutableLiveData<WeatherResponse> weatherInfo;
    private final MutableLiveData<Boolean> success;
    private final MutableLiveData<String> location;
    private final WeatherRepository weatherRepo;

    private final CompositeDisposable disposables;

    // ----- View Models -----

    public final MutableLiveData<Boolean> noResult;
    public final MutableLiveData<String> cityName;
    public final MutableLiveData<Integer> currentTemperature;
    public final MutableLiveData<String> description;
    private final MutableLiveData<String> imageUrl;
    public final MutableLiveData<String> firstDayName;
    public final MutableLiveData<Integer> firstDayTemp;
    public final MutableLiveData<String> secondDayName;
    public final MutableLiveData<Integer> secondDayTemp;
    public final MutableLiveData<String> thirdDayName;
    public final MutableLiveData<Integer> thirdDayTemp;
    public final MutableLiveData<String> fourthDayName;
    public final MutableLiveData<Integer> fourthDayTemp;

    public WeatherViewModel() {
        this.weatherInfo = new MutableLiveData<>(null);
        this.success = new MutableLiveData<>(false);
        this.location = new MutableLiveData<>("ha noi");

        weatherRepo = new WeatherRepository();
        disposables = new CompositeDisposable();

        this.noResult = new MutableLiveData<>(false);
        this.cityName = new MutableLiveData<>("");
        this.currentTemperature = new MutableLiveData<>(20);
        this.description = new MutableLiveData<>("Cloudy day");
        this.imageUrl = new MutableLiveData<>("");
        this.firstDayName = new MutableLiveData<>("");
        this.firstDayTemp = new MutableLiveData<>(0);
        this.secondDayName = new MutableLiveData<>("");
        this.secondDayTemp = new MutableLiveData<>(0);
        this.thirdDayName = new MutableLiveData<>("");
        this.thirdDayTemp = new MutableLiveData<>(0);
        this.fourthDayName = new MutableLiveData<>("");
        this.fourthDayTemp = new MutableLiveData<>(0);
    }

    public LiveData<WeatherResponse> getWeatherInfo() {
        return weatherInfo;
    }

    public LiveData<Boolean> getSuccess() {
        return success;
    }

    public void loadWeatherInfo() {

        if (location.getValue() == null)
            return;

        Log.d("Weather", location.getValue());

        disposables.add(
                weatherRepo.loadWeatherInfo(location.getValue().trim())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<WeatherResponse>() {

                            @Override
                            public void onSuccess(@NonNull WeatherResponse weatherResponse) {
                                success.setValue(true);
                                weatherInfo.setValue(weatherResponse);


                                String location = weatherResponse.getCity().getName();
                                int currTemp = temperature_current(weatherResponse.getList().get(0).getMain().getTemp());

                                String iconURL = weatherResponse.getList().get(0).getWeather().get(0).getIcon();
                                String iconURL1 = weatherResponse.getList().get(1).getWeather().get(0).getIcon();
                                String iconURL2 = weatherResponse.getList().get(2).getWeather().get(0).getIcon();
                                String iconURL3 = weatherResponse.getList().get(3).getWeather().get(0).getIcon();
                                String iconURL4 = weatherResponse.getList().get(4).getWeather().get(0).getIcon();
                                String imgUrl = "https://openweathermap.org/img/wn/" + iconURL + ".png";

//                    String imageUrl1 = "https://openweathermap.org/img/wn/" + iconURL1 + ".png";
//                    String imageUrl2 = "https://openweathermap.org/img/wn/" + iconURL2 + ".png";
//                    String imageUrl3 = "https://openweathermap.org/img/wn/" + iconURL3 + ".png";
//                    String imageUrl4 = "https://openweathermap.org/img/wn/" + iconURL4 + ".png";

                                String desc = weatherResponse.getList().get(0).getWeather().get(0).getDescription();


                                String dayName1 = dayName(weatherResponse.getList().get(0).getDt());
                                int dayTemp1 = temperature(
                                        weatherResponse.getList().get(2).getMain().getTemp_min(),
                                        weatherResponse.getList().get(0).getMain().getTemp_max()
                                );

                                String dayName2 = dayName(weatherResponse.getList().get(9).getDt());
                                int dayTemp2 = temperature(
                                        weatherResponse.getList().get(13).getMain().getTemp_min(),
                                        weatherResponse.getList().get(9).getMain().getTemp_max()
                                );

                                String dayName3 = dayName(weatherResponse.getList().get(16).getDt());
                                int dayTemp3 = temperature(
                                        weatherResponse.getList().get(18).getMain().getTemp_min(),
                                        weatherResponse.getList().get(15).getMain().getTemp_max()
                                );

                                String dayName4 = dayName(weatherResponse.getList().get(23).getDt());
                                int dayTemp4 = temperature(
                                        weatherResponse.getList().get(20).getMain().getTemp_min(),
                                        weatherResponse.getList().get(24).getMain().getTemp_max()
                                );


                                if ((location != null && !location.isEmpty()) &&
//                            (sunSet != null && !sunSet.isEmpty()) &&
                                        (desc != null && !desc.isEmpty()) &&
                                        (iconURL != null && !iconURL.isEmpty()) &&
                                        (imgUrl != null && !imgUrl.isEmpty()) &&
                                        (dayName1 != null && !dayName1.isEmpty())
                                ) {
                                    noResult.setValue(false);
                                    cityName.setValue(location);
                                    currentTemperature.setValue(currTemp);
                                    imageUrl.setValue(imgUrl);
                                    description.setValue(desc);

                                    firstDayName.setValue(dayName1);
                                    firstDayTemp.setValue(dayTemp1);
                                    secondDayName.setValue(dayName2);
                                    secondDayTemp.setValue(dayTemp2);
                                    thirdDayName.setValue(dayName3);
                                    thirdDayTemp.setValue(dayTemp3);
                                    fourthDayName.setValue(dayName4);
                                    fourthDayTemp.setValue(dayTemp4);

                                } else
                                    noResult.setValue(true);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                success.setValue(false);
                                weatherInfo.setValue(null);

                                noResult.setValue(true);
                                location.setValue("");
                            }
                        })
        );
    }

    private int temperature(double temp_max, double temp_min) {
        int temperature_max = (int) Math.round(temp_max - 273);
        int temperature_min = (int) Math.round(temp_min - 273);
        return (temperature_max + temperature_min) / 2;
    }

    private int temperature_current(double temp) {
        return (int) Math.round(temp - 273);
    }

    private String timeStamp(int timeStamp) {

        Date date = new Date(timeStamp * 1000L);
        SimpleDateFormat jdf = new SimpleDateFormat("HH:mm a");
        jdf.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        return jdf.format(date);

    }

    public String formatTemperature(int temp) {
        return temp + "°C";
    }

    private String dayName(int timeStamp) {
        Date date = new Date(timeStamp * 1000L);
        SimpleDateFormat jdf = new SimpleDateFormat("EEE");
        jdf.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        return jdf.format(date);
    }

    public MutableLiveData<String> getImageUrl() {
        return imageUrl;
    }

    public MutableLiveData<String> getLocation() {
        return location;
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }
}
