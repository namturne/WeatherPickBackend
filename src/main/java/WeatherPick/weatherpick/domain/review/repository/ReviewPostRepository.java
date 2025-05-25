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
            "select "+
            "R.reviewpost_id AS reviewId, "+
            "R.title AS title, "+
            "R.content AS content, "+
            "R.createdDate AS createDate, "+
            "U.nickname AS writerNickname, "+
            "U.username AS writerUsername "+
            "from db1.ReviewPostEntity AS R "+
            "inner join db1.UserEntity AS U "+
            "on R.user_key = U.user_key "+
            "WHERE reviewpost_id = ?1",
            nativeQuery = true
    )
    GetReviewPostResultSet getReview(Long reviewId);

}
