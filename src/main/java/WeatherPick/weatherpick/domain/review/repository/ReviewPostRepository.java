package WeatherPick.weatherpick.domain.review.repository;

import WeatherPick.weatherpick.domain.review.entity.ReviewPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewPostRepository extends JpaRepository<ReviewPostEntity, Long> {
    List<ReviewPostEntity> findAllByUser_UserKey(Long userKey);
    ReviewPostEntity findByReviewId(Long reviewId);

    @Query("SELECT r FROM ReviewPostEntity r JOIN FETCH r.user")
    List<ReviewPostEntity> findByOrderByWriteDateDesc();
    @Query(
            value =
            "select "+
            "R.reviewpost_id AS reviewId, "+
            "R.title AS title, "+
            "R.content AS content, "+
            "R.writeDate AS writeDate, "+
            "U.nickname AS writerNickname, "+
            "U.username AS writerUsername, "+
            "R.likeCount AS likeCount, "+
            "R.scrapCount AS scrapCount, "+
            "R.commentCount AS commentCount, "+
            "R.ViewCount AS viewCount "+
            "from db1.ReviewPostEntity AS R "+
            "inner join db1.UserEntity AS U "+
            "on R.user_key = U.user_key "+
            "WHERE reviewpost_id = ?1",
            nativeQuery = true
    )
    GetReviewPostResultSet getReview(Long reviewId);



}
