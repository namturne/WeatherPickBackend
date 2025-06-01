package WeatherPick.weatherpick.domain.review.entity;

import WeatherPick.weatherpick.domain.place.entity.PlaceEntity;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.Instant;
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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewpost_id")
    private Long reviewId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_key", nullable = false)
    private UserEntity user;

    //@ManyToOne
    //@JoinColumn(name = "place_key", nullable = false) 임시수정
    //@Column
    //private String place;

    @Column
    private int likeCount=0;

    @Column
    private int scrapCount = 0;
    @Column
    private int viewCount =0;

    @Column
    private int commentCount =0;

    @Column
    String writeDate = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()));


    public void increaseViewCount(){
        this.viewCount++;
    }
    public void increaseFavoriteCount(){
        this.likeCount++;
    }
    public void decreaseFavoriteCount(){
        this.likeCount--;
    }
}
