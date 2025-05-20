package WeatherPick.weatherpick.config;

import WeatherPick.weatherpick.domain.place.entity.PlaceEntity;
import WeatherPick.weatherpick.domain.place.repository.PlaceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

// 프로젝트 실행시 테스트용 더미데이터 생성
//
@Component
public class SamplePlaceData implements CommandLineRunner {

    private final PlaceRepository placeRepository;

    public SamplePlaceData(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public void run(String... args){
        if(placeRepository.count() == 0){   // DB에 데이터 있으면 등록 x
            List<PlaceEntity> places = List.of(
                    new PlaceEntity("홍대 카페 A", "서울 마포구 와우산로", 37.556, 126.923, "카페", 0f, 0),
                    new PlaceEntity("강남 식당 B", "서울 강남구 테헤란로", 37.498, 127.027, "식당", 0f, 0),
                    new PlaceEntity("잠실 공원 C", "서울 송파구 올림픽로", 37.520, 127.120, "공원", 0f, 0),
                    new PlaceEntity("망원 카페 D", "서울 마포구 망원로", 37.555, 126.904, "카페", 0f, 0),
                    new PlaceEntity("종로 식당 E", "서울 종로구 종로3길", 37.571, 126.991, "식당", 0f, 0),
                    new PlaceEntity("남산 공원 F", "서울 중구 소파로", 37.550, 126.990, "공원", 0f, 0)
            );
            placeRepository.saveAll(places);
            System.out.println("SamplePlaceData 등록 완료 (" + places.size() + "개)");
        }
    }
}
