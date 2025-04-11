package WeatherPick.weatherpick.domain.review.entity;

import WeatherPick.weatherpick.domain.place.entity.PlaceEntity;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import org.apache.catalina.User;

import java.util.Date;
// 리뷰 게시글 엔티티
/*
post_id (PK)	BIGINT	    게시글 ID
user_id (FK)	BIGINT	    작성자
place_id (FK)	BIGINT	    리뷰한 장소
title	        VARCHAR	    게시글 제목
content	        TEXT	    게시글 내용
location_score	INT (1~5)	장소에 대한 위치 점수
created_at	    DATETIME	작성 시간
*/

@Entity
public class ReviewPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_post_id; // 리뷰 게시글 ID (pk)

    @Column(nullable = false)
    private String review_post_title; // 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String review_post_content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user; // 유저키 (fk)

    @ManyToOne
    @JoinColumn(name = "place_key", nullable = false)
    private PlaceEntity place;

    @Column(nullable = false)
    private int rating; // 별점

    @Column(nullable = false)
    private int scrapCnt = 0; // 스크랩 수

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

}
