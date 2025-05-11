package WeatherPick.weatherpick.domain.review.repository;

import WeatherPick.weatherpick.domain.review.entity.ReviewCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewCommentRepository extends JpaRepository<ReviewCommentEntity, Long> {

    @Query("SELECT c FROM ReviewCommentEntity c WHERE c.post.reviewpost_id = :postId")
    List<ReviewCommentEntity> findByPostId(@Param("postId") Long postId);
}