package WeatherPick.weatherpick.domain.review.repository;

public interface GetReviewPostResultSet {
    Long getReviewId();
    String getTitle();
    String getContent();
    String getWriteDate();
    String getWriterNickname();
    String getWriterUsername();
    Integer getLikeCount();
    Integer getScrapCount();
    Integer getViewCount();
    Integer getCommentCount();

    String getCreateDate();
}
