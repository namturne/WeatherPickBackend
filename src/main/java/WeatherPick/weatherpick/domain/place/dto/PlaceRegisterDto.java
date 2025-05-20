package WeatherPick.weatherpick.domain.place.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceRegisterDto {
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String category;

    public PlaceRegisterDto(String placename, String address, Double placelatitude, Double placelongitude, String category) {
    }
}
