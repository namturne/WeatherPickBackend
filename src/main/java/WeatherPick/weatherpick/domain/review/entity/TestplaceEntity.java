package WeatherPick.weatherpick.domain.review.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "testplace")
@Table(name= "testplace")
public class TestplaceEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sequence;
    private Long reviewNumber;
    private String place;

    public TestplaceEntity(Long reviewNumber, String place){
        this.reviewNumber = reviewNumber;
        this.place = place;
    }
}
