package nhom9.watchluxury.data.model;

public class Temperature {

    private double temp;

    private double feelsLike;

    private double temp_min;

    private double temp_max;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public Temperature(double temp, double feelsLike, double temp_min, double temp_max) {
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }
}
