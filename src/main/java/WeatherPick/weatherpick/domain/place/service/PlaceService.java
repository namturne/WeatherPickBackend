package WeatherPick.weatherpick.domain.place.service;

import WeatherPick.weatherpick.domain.place.dto.PlaceDetailDto;
import WeatherPick.weatherpick.domain.place.dto.PlaceRegisterDto;
import WeatherPick.weatherpick.domain.place.entity.PlaceEntity;
import WeatherPick.weatherpick.domain.place.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    //장소 등록
    public void registerPlace(PlaceRegisterDto placeRegisterDto) {
        PlaceEntity place = new PlaceEntity();

        place.setPlacename(placeRegisterDto.getName());
        place.setPlacelatitude(placeRegisterDto.getLatitude());
        place.setPlacelongitude(placeRegisterDto.getLongitude());
        place.setScrapCount(0);
        placeRepository.save(place);
    }
    // 검색 / 필터 기능
    public List<PlaceEntity> searchPlaces(String keyword, String category) {
        if (keyword != null && category != null) {
            return placeRepository.findByCategoryIgnoreCaseAndPlacenameContainingIgnoreCaseOrAddressContainingIgnoreCase(
                    category, keyword, keyword);
        } else if (keyword != null) {
            return placeRepository.findByPlacenameContainingIgnoreCaseOrAddressContainingIgnoreCase(keyword, keyword);
        } else if (category != null) {
            return placeRepository.findByCategoryIgnoreCase(category);
        } else {
            return placeRepository.findAll();
        }
    }

    //  전체 조회
    public List<PlaceRegisterDto> getAllplaces() {
        return placeRepository.findAll().stream()
                .map(p -> new PlaceRegisterDto(
                        p.getPlacename(),
                        p.getAddress(),
                        p.getPlacelatitude(),
                        p.getPlacelongitude(),
                        p.getCategory()
                ))
                .collect(Collectors.toList());
    }

    // 상세 조회
    public PlaceDetailDto getPlaceDetail(Long placeId) {
        PlaceEntity place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장소가 존재하지 않습니다: id = " + placeId));

        return new PlaceDetailDto(
                place.getPlace_key(),
                place.getPlacename(),
                place.getAddress(),
                place.getPlacelatitude(),
                place.getPlacelongitude(),
                place.getCategory(),
                place.getAvgRating(),
                place.getScrapCount()
        );
    }
}
