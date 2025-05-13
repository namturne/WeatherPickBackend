package WeatherPick.weatherpick.domain.review.repository;

import WeatherPick.weatherpick.domain.review.entity.ReviewRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRatingRepository extends JpaRepository<ReviewRatingEntity, Void> {
    List<ReviewRatingEntity> findByUser_UserKeyAndScrapedTrue(Long userKey);
}
