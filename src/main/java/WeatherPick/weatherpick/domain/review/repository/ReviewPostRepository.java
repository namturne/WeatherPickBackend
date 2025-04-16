package WeatherPick.weatherpick.domain.review.repository;

import WeatherPick.weatherpick.domain.review.entity.ReviewPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewPostRepository extends JpaRepository<ReviewPostEntity, Long> {

}
