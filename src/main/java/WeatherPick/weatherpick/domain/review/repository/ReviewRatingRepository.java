package WeatherPick.weatherpick.domain.review.repository;

import WeatherPick.weatherpick.domain.review.entity.ReviewRatingEntity;
import WeatherPick.weatherpick.domain.review.entity.ReviewRatingKey;
import WeatherPick.weatherpick.domain.review.entity.ReviewPostEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ReviewRatingRepository
        extends JpaRepository<ReviewRatingEntity, ReviewRatingKey> {

    @Query("SELECT r.post FROM ReviewRatingEntity r WHERE r.user.user_key = :userKey AND r.isScraped = true")
    List<ReviewPostEntity> findScrappedPostsByUser(@Param("userKey") Long userKey);
}
