package WeatherPick.weatherpick.domain.review.entity;

import WeatherPick.weatherpick.domain.place.entity.PlaceEntity;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import java.util.Date;

// 리뷰 게시글 엔티티
/*
post_id (PK)	  BIGINT	    게시글 ID
user_id (FK)	  BIGINT	    작성자
place_id (FK)	  BIGINT	    리뷰한 장소
title	          VARCHAR	    게시글 제목
content	        TEXT	      게시글 내용
rating         	INT (1~5)	  장소에 대한 위치 점수
createddate	    DATETIME	  작성 시간
*/

@Entity
public class ReviewPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewpost_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_key", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "place_key", nullable = false)
    private PlaceEntity place;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private int scrapCount = 0;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

    // ─── getters/setters ───
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public PlaceEntity getPlace() { return place; }
    public void setPlace(PlaceEntity place) { this.place = place; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public int getScrapCount() { return scrapCount; }
    public void setScrapCount(int scrapCount) { this.scrapCount = scrapCount; }

    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
}
