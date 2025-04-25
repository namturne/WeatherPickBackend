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

        place.setPlace_name(placeRegisterDto.getPlaceName());
        place.setPlace_latitude(placeRegisterDto.getLatitude());
        place.setPlace_longitude(placeRegisterDto.getLongitude());
        place.setScrap_Count(0);
        placeRepository.save(place);
    }
}
