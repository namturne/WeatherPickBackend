package WeatherPick.weatherpick.domain.scrap.repository;

import WeatherPick.weatherpick.domain.review.entity.ReviewPostEntity;
import WeatherPick.weatherpick.domain.scrap.entity.ScrapEntity;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<ScrapEntity, Long> {
    Optional<ScrapEntity> findByUserAndReviewPost(UserEntity user, ReviewPostEntity reviewPost);
    List<ScrapEntity> findByUser(UserEntity user);
}
