package WeatherPick.weatherpick.domain.place.service;

import WeatherPick.weatherpick.domain.place.dto.PlaceRegisterDto;
import WeatherPick.weatherpick.domain.place.entity.PlaceEntity;
import WeatherPick.weatherpick.domain.place.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {
    private PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    //장소 등록
    public void registerPlace(PlaceRegisterDto placeRegisterDto) {
        PlaceEntity place = new PlaceEntity();

        place.setplacename(placeRegisterDto.getPlaceName());
        place.setplacelatitude(placeRegisterDto.getLatitude());
        place.setplacelongitude(placeRegisterDto.getLongitude());
        place.setCategory(placeRegisterDto.getCategory());
        place.setAddress(placeRegisterDto.getAddress());
        place.setScrap_Count(0);
        placeRepository.save(place);
    }

    //장소 전체 조회
    public List<PlaceRegisterDto> getAllplaces () {
        return placeRepository.findAll().stream()
                .map(p -> new PlaceRegisterDto(
                        p.getplacename(),
                        p.getplacelatitude(),
                        p.getplacelongitude(),
                        p.getCategory(),
                        p.getAddress(),
                        p.getScrap_Count()
                )).toList();
    }
}
