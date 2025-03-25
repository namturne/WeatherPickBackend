package WeatherPick.weatherpick.domain.weather.dto;

public class WeatherDto {
    private String time;
    private String temperature;
    private String humidity;
    private String windSpeed;
    private String precipitation;
    private String sky;

    public WeatherDto(String time, String temperature, String humidity, String windSpeed, String precipitation, String sky){
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.precipitation = precipitation;
        this.sky = sky;
    }
}
