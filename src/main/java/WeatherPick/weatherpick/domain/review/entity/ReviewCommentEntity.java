package WeatherPick.weatherpick.domain.review.entity;

import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import jakarta.persistence.*;

import java.util.Date;
//리뷰 댓글
/*
필드명	        타입	            설명
comment_id (PK)	BIGINT	    댓글 ID
post_id (FK)	BIGINT	    소속 리뷰 게시글
user_id (FK)	BIGINT	    작성 유저
content	        TEXT	    댓글 내용
created_at	    DATETIME	작성 시간
 */

@Entity
public class ReviewCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_comment_id;

    @ManyToOne
    @JoinColumn(name = "review_post_id",nullable = false)
    private ReviewPostEntity post;

    @ManyToOne
    @JoinColumn(name = "user_key",nullable = false)
    private UserEntity user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String review_comment_content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date review_comment_createdAt = new Date();

    public Long getReview_comment_id() {
        return review_comment_id;
    }

    public void setReview_comment_id(Long review_comment_id) {
        this.review_comment_id = review_comment_id;
    }

    public ReviewPostEntity getPost() {
        return post;
    }

    public void setPost(ReviewPostEntity post) {
        this.post = post;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getReview_comment_content() {
        return review_comment_content;
    }

    public void setReview_comment_content(String review_comment_content) {
        this.review_comment_content = review_comment_content;
    }

    public Date getReview_comment_createdAt() {
        return review_comment_createdAt;
    }

    public void setReview_comment_createdAt(Date review_comment_createdAt) {
        this.review_comment_createdAt = review_comment_createdAt;
    }
}