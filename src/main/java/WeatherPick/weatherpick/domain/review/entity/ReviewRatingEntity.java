package WeatherPick.weatherpick.domain.review.entity;

import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import jakarta.persistence.*;

//게시글 별점
/*
필드명	        타입	            설명
user_id (FK)	BIGINT	    별점 준 유저
post_id (FK)	BIGINT	    대상 리뷰 게시글
rating	        INT (1~5)	별점 수치
is_scraped	    BOOLEAN	        사용자가 해당 리뷰를 스크랩했는지 여부
(PK = user_id + post_id)	복합 기본키	한 유저가 한 게시글에 한 번만 평가 가능
                   ==> ReviewRatingKey.java
 */

@Entity
@IdClass(ReviewRatingKey.class)
public class ReviewRatingEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_key")
    private UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "review_post_id")
    private ReviewPostEntity post;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private Boolean isScraped;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ReviewPostEntity getPost() {
        return post;
    }

    public void setPost(ReviewPostEntity post) {
        this.post = post;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getScraped() {
        return isScraped;
    }

    public void setScraped(Boolean scraped) {
        isScraped = scraped;
    }
}
