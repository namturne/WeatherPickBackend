package WeatherPick.weatherpick.domain.user.entity;

import jakarta.persistence.*;

@Entity
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int rating; //별점 (1~5)

}
