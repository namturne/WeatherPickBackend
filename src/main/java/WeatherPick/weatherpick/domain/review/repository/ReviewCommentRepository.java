package WeatherPick.weatherpick.domain.review.repository;

import WeatherPick.weatherpick.domain.review.entity.ReviewCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewCommentRepository extends JpaRepository<ReviewCommentEntity, Long> {

    @Query(
        value =
                "SELECT " +
                "U.nickname AS nickname, " +
                "DATE_FORMAT(C.writeDateTime, '%Y-%m-%d %H:%i') AS writeDateTime, " +
                "C.content AS content " +
                "FROM db1.ReviewCommentEntity AS C " +
                "INNER JOIN db1.UserEntity AS U " +
                "ON C.userId = U.userId " +
                "WHERE C.reviewPostId = ?1 " +
                "ORDER BY C.writeDateTime ASC",
            nativeQuery = true
    )
    List<GetReviewCommentResultSet> getCommentList(Long ReviewId);
}
