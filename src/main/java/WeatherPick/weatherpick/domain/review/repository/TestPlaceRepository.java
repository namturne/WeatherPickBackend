package WeatherPick.weatherpick.domain.review.repository;

import WeatherPick.weatherpick.domain.review.entity.TestplaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestPlaceRepository extends JpaRepository<TestplaceEntity,Long> {

}
