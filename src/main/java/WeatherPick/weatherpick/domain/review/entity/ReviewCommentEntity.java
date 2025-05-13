package WeatherPick.weatherpick.domain.review.entity;

import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import java.util.Date;

//리뷰 댓글
/*
필드명	          타입	    설명
comment_id (PK)	BIGINT	  댓글 ID
post_id (FK)	  BIGINT	  소속 리뷰 게시글
user_id (FK)	  BIGINT	  작성 유저
content	        TEXT	    댓글 내용
createddate	    DATETIME	작성 시간
 */

@Entity
public class ReviewCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewcomment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reviewpost_id", nullable = false)
    private ReviewPostEntity post;

    @ManyToOne
    @JoinColumn(name = "user_key", nullable = false)
    private UserEntity user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

    // ─── getters/setters ───
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ReviewPostEntity getPost() { return post; }
    public void setPost(ReviewPostEntity post) { this.post = post; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
}
