package WeatherPick.weatherpick.domain.scrap.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScrapResponseDTO {
    private Long scrapId;
    private Long userId;
    private Long reviewpostId;
}
