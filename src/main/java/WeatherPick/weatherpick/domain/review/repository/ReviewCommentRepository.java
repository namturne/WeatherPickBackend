package WeatherPick.weatherpick.domain.review.repository;

import WeatherPick.weatherpick.domain.review.entity.ReviewCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewCommentRepository extends JpaRepository<ReviewCommentEntity, Long> {
    List<ReviewCommentEntity> findByPost_ReviewId(Long postReviewId);

    // ▶ 내가 쓴 댓글 조회용
    List<ReviewCommentEntity> findByUser_UserKey(Long userKey);
}
