package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.domain.place.dto.PlaceRegisterDto;
import WeatherPick.weatherpick.domain.place.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/place")
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public String showPlaceForm() {
        return "place";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> createPlace(PlaceRegisterDto placeDTO) {
        placeService.registerPlace(placeDTO);
        return ResponseEntity.ok("장소 등록 성공");
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<Iterable<PlaceRegisterDto>> getAllplaces() {
        List<PlaceRegisterDto> places = placeService.getAllplaces();
        return ResponseEntity.ok(places);
    }
}
