package WeatherPick.weatherpick.domain.review.repository;

import WeatherPick.weatherpick.domain.review.entity.ReviewPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewPostRepository extends JpaRepository<ReviewPostEntity, Long> {

    List<ReviewPostEntity> findAllByUser_UserKey(Long userKey);

    ReviewPostEntity findByReviewId(Long reviewId);

    @Query(
            value =
                    "SELECT " +
                            "R.reviewpost_id     AS reviewId, " +
                            "R.title             AS title, " +
                            "R.content           AS content, " +
                            "R.created_date      AS createDate, " +
                            "U.nickname          AS writerNickname, " +
                            "U.username          AS writerUsername " +
                            "FROM review_post_entity R " +
                            "INNER JOIN user_entity U ON R.user_key = U.user_key " +
                            "WHERE R.reviewpost_id = ?1",
            nativeQuery = true
    )
    GetReviewPostResultSet getReview(Long reviewId);
}
