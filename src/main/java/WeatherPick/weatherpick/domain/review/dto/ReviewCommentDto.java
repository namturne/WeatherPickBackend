package WeatherPick.weatherpick.domain.review.dto;

import java.util.Date;

/*
필드명                    타입       설명
review_comment_id (PK)   BIGINT    댓글 ID
username                 VARCHAR   댓글 작성자의 아이디 혹은 이름
review_comment_content   TEXT      댓글 내용
review_comment_createdAt DATETIME  댓글 작성 시간
*/

public class ReviewCommentDto {

    private Long review_comment_id;
    private String username;
    private String review_comment_content;
    private Date review_comment_createdAt;

    public ReviewCommentDto(Long review_comment_id, String username, String review_comment_content, Date review_comment_createdAt) {
        this.review_comment_id = review_comment_id;
        this.username = username;
        this.review_comment_content = review_comment_content;
        this.review_comment_createdAt = review_comment_createdAt;
    }

    public Long getReview_comment_id() {
        return review_comment_id;
    }

    public void setReview_comment_id(Long review_comment_id) {
        this.review_comment_id = review_comment_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
