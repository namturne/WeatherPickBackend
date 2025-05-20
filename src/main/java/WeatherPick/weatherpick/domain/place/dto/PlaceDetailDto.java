package WeatherPick.weatherpick.domain.place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceDetailDto {
    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String category;
    private Float avgRating;
    private Integer scrapCount;
}
