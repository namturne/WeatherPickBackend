package WeatherPick.weatherpick.domain.review.repository;

import WeatherPick.weatherpick.domain.review.entity.ReviewPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewPostRepository extends JpaRepository<ReviewPostEntity, Long> {

    @Query("SELECT p FROM ReviewPostEntity p WHERE p.user.user_key = :userKey")
    List<ReviewPostEntity> findAllByUserKey(@Param("userKey") Long userKey);
}
