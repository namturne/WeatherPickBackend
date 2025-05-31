package WeatherPick.weatherpick.domain.review.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewPostRequestDto {
    private String title;
    private String content;
    private List<String> placeList;

}
