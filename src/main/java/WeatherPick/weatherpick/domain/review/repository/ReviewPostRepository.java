package WeatherPick.weatherpick.domain.review.repository;

import WeatherPick.weatherpick.domain.review.entity.ReviewPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewPostRepository extends JpaRepository<ReviewPostEntity, Long> {
    List<ReviewPostEntity> findAllByUser_UserKey(Long userKey);
}
