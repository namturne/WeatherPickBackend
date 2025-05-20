package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.domain.place.dto.PlaceDetailDto;
import WeatherPick.weatherpick.domain.place.dto.PlaceRegisterDto;
import WeatherPick.weatherpick.domain.place.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    // 장소 등록
    @PostMapping
    public ResponseEntity<String> createPlace(@RequestBody PlaceRegisterDto placeDTO) {
        placeService.registerPlace(placeDTO);
        return ResponseEntity.ok("장소 등록 성공");
    }

    // 전체 장소 조회
    @GetMapping("/all")
    public ResponseEntity<List<PlaceRegisterDto>> getAllPlaces() {
        return ResponseEntity.ok(placeService.getAllplaces());
    }

    //장소 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<PlaceDetailDto> getPlaceById(@PathVariable Long id) {
        PlaceDetailDto place = placeService.getPlaceDetail(id);
        return ResponseEntity.ok(place);
    }
}
