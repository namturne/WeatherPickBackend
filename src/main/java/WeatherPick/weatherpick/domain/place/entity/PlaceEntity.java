package WeatherPick.weatherpick.domain.place.entity;

import jakarta.persistence.*;
/*
필드명	        타입	            설명
placeid (PK)	BIGINT	    장소 고유 식별자
name	        VARCHAR	    장소 이름
latitude	    DOUBLE	        위도
longitude	    DOUBLE	        경도
category	    VARCHAR	        카페, 식당 등
address         VARCHAR         도로명 주소
avg_rating	    FLOAT	        평균 별점
scrap_count	    INT	            스크랩 횟수
 */

@Entity
public class PlaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long place_key;

    @Column(nullable = false)
    private String placename;

    @Column(nullable = false)
    private Double placelatitude; // 위도

    @Column(nullable = false)
    private Double placelongitude; // 경도

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String address;

    @Column
    private int scrap_Count;

    public Long getplacekey() {
        return place_key;
    }

    public void setplacekey(Long placekey) {
        this.place_key = placekey;
    }

    public String getplacename() {
        return placename;
    }

    public void setplacename(String placename) {
        this.placename = placename;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getplacelongitude() {
        return placelongitude;
    }

    public void setplacelongitude(Double placelongitude) {
        this.placelongitude = placelongitude;
    }

    public int getScrap_Count() {
        return scrap_Count;
    }

    public void setScrap_Count(int scrap_Count) {
        this.scrap_Count = scrap_Count;
    }

    public Double getplacelatitude() {
        return placelatitude;
    }

    public void setplacelatitude(Double placelatitude) {
        this.placelatitude = placelatitude;
    }
}
