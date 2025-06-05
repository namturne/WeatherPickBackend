package WeatherPick.weatherpick.domain.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewPostRequestDto {
    private String title;
    private String content;
    private List<PlaceDto> places;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PlaceDto {
        private String title;
        private String address;
        private String roadAddress;
        private String mapx;
        private String mapy;
        private String category;
        private String link;
    }
}
