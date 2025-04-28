package WeatherPick.weatherpick.domain.place.service;

import WeatherPick.weatherpick.domain.place.dto.PlaceRegisterDto;
import WeatherPick.weatherpick.domain.place.entity.PlaceEntity;
import WeatherPick.weatherpick.domain.place.repository.PlaceRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaceRegistgerService {
    private PlaceRepository placeRepository;

    public PlaceRegistgerService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public void registerPlace(PlaceRegisterDto placeRegisterDto) {
        PlaceEntity place = new PlaceEntity();

        place.setplacename(placeRegisterDto.getPlaceName());
        place.setplacelatitude(placeRegisterDto.getLatitude());
        place.setplacelongitude(placeRegisterDto.getLongitude());
        place.setScrap_Count(0);
        placeRepository.save(place);
    }
}
