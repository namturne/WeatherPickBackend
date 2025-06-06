package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.domain.review.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public String upload(
            @RequestParam(value = "file", required = true) MultipartFile file
    ){
        return fileService.upload(file);
    }

    @GetMapping(value = "{fileName}",produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
    public Resource getImage(
        @PathVariable("fileName") String fileName
    ){

        return fileService.getImage(fileName);
    }

}
