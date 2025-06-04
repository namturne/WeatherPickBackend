package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.domain.scrap.DTO.ScrapRequestDTO;
import WeatherPick.weatherpick.domain.scrap.DTO.ScrapResponseDTO;
import WeatherPick.weatherpick.domain.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(("/api/scrap"))
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping
    public ScrapResponseDTO addscrap(@RequestBody ScrapRequestDTO dto){
        return scrapService.addScrap(dto);
    }

    @DeleteMapping
    public void deleteScrap(@RequestParam Long userId, @RequestParam Long postId) {
        scrapService.deleteScrap(userId, postId);
    }

    @GetMapping("/{userId}")
    public List<ScrapResponseDTO> getUserScraps(@PathVariable Long userId) {
        return scrapService.getScrapsByUserId(userId);
    }
}
