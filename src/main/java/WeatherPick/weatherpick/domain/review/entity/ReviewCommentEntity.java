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
    @JoinColumn(name = "post_id", nullable = false)
    private ReviewPostEntity post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String review_comment_content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date review_comment_createdAt = new Date();

}