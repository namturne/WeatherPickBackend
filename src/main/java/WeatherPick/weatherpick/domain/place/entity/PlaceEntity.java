package WeatherPick.weatherpick.domain.place.entity;

import jakarta.persistence.*;
/*
필드명	        타입	            설명
place_id (PK)	BIGINT	    장소 고유 식별자
name	        VARCHAR	    장소 이름
latitude	    DOUBLE	        위도
longitude	    DOUBLE	        경도
category	    VARCHAR	        카페, 식당 등
avg_rating	    FLOAT	        평균 별점
scrap_count	    INT	            스크랩 횟수
 */

@Entity
public class PlaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long place_key;

    @Column(nullable = false)
    private String place_name;

    @Column(nullable = false)
    private Double place_latitude; // 위도

    @Column(nullable = false)
    private Double place_longitude; // 경도

    @Column
    private int scrap_Count;
}
