package WeatherPick.weatherpick.domain.place.repository;

import WeatherPick.weatherpick.domain.place.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<PlaceEntity, Long> {

    //  장소명 or 주소에 keyword 포함 + 카테고리 조건
    List<PlaceEntity> findByPlacenameContainingIgnoreCaseOrAddressContainingIgnoreCase(String nameKeyword, String addressKeyword);

    //  카테고리만 필터
    List<PlaceEntity> findByCategoryIgnoreCase(String category);

    //  keyword + category 조합
    List<PlaceEntity> findByCategoryIgnoreCaseAndPlacenameContainingIgnoreCaseOrAddressContainingIgnoreCase(
            String category, String nameKeyword, String addressKeyword);
}
