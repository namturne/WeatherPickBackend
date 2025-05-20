package WeatherPick.weatherpick.domain.place.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/*
필드명	        타입	            설명
placeid (PK)	BIGINT	    장소 고유 식별자
name	        VARCHAR	    장소 이름
latitude	    DOUBLE	        위도
longitude	    DOUBLE	        경도
category	    VARCHAR	        카페, 식당 등
avg_rating	    FLOAT	        평균 별점
scrapCount	    INT	            스크랩 횟수
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PlaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long place_key;

    @Column(nullable = false)
    private String placename;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Double placelatitude; // 위도

    @Column(nullable = false)
    private Double placelongitude; // 경도

    @Column
    private int scrapCount;

    @Column
    private Float avgRating;

    public PlaceEntity(String name, String address, Double latitude, Double longitude,
                 String category, Float avgRating, Integer scrapCount) {
        this.placename = name;
        this.address = address;
        this.placelatitude = latitude;
        this.placelongitude = longitude;
        this.category = category;
        this.avgRating = avgRating;
        this.scrapCount = scrapCount;
    }
}
