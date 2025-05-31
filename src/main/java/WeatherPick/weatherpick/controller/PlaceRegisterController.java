package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.domain.place.dto.PlaceRegisterDto;
import WeatherPick.weatherpick.domain.place.service.PlaceRegistgerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/place")
public class PlaceRegisterController {

    private final PlaceRegistgerService placeRegistgerService;

    public PlaceRegisterController(PlaceRegistgerService placeRegistgerService) {
        this.placeRegistgerService = placeRegistgerService;
    }

    // 장소 등록 화면
    @GetMapping
    public String showPlaceForm() {
        return "place";
    }

    // 장소 등록 API (JSON Body를 받을 때 @RequestBody 필요)
    @PostMapping
    @ResponseBody
    public ResponseEntity<String> createPlace(@RequestBody PlaceRegisterDto placeDTO) {
        placeRegistgerService.registerPlace(placeDTO);
        return ResponseEntity.ok("장소 등록 성공");
    }
}
