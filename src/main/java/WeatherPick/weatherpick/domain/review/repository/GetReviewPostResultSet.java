package WeatherPick.weatherpick.domain.review.repository;

public interface GetReviewPostResultSet {
    Long getReviewId();
    String getTitle();
    String getContent();
    String getCreateDate();
    String getWriterNickname();
    String getWriterUsername();
}
