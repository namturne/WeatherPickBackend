package WeatherPick.weatherpick.domain.place.dto;





public class PlaceRegisterDto {
    private String placeName;
    private Double latitude;
    private Double longitude;
    private int scrap_cnt = 0;

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getScrap_cnt() {
        return scrap_cnt;
    }

    public void setScrap_cnt(int scrap_cnt) {
        this.scrap_cnt = scrap_cnt;
    }
}
