package WeatherPick.weatherpick.domain.place.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceRegisterDto {
    private String placeName;
    private Double latitude;
    private Double longitude;
    private int scrap_cnt = 0;

}
