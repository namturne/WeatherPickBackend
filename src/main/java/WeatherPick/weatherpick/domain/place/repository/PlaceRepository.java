package WeatherPick.weatherpick.domain.place.repository;

import WeatherPick.weatherpick.domain.place.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<PlaceEntity, Long> {
}
