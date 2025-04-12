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

    public Long getPlace_key() {
        return place_key;
    }

    public void setPlace_key(Long place_key) {
        this.place_key = place_key;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public Double getPlace_longitude() {
        return place_longitude;
    }

    public void setPlace_longitude(Double place_longitude) {
        this.place_longitude = place_longitude;
    }

    public int getScrap_Count() {
        return scrap_Count;
    }

    public void setScrap_Count(int scrap_Count) {
        this.scrap_Count = scrap_Count;
    }

    public Double getPlace_latitude() {
        return place_latitude;
    }

    public void setPlace_latitude(Double place_latitude) {
        this.place_latitude = place_latitude;
    }
}
