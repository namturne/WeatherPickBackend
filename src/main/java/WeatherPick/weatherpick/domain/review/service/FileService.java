package WeatherPick.weatherpick.domain.review.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.path}")
    private String filePath;
    @Value("${file.url}")
    private String fileUrl;

    public String upload(MultipartFile file){
        if(file.isEmpty()) return null;

        // 폴더가 없으면 생성
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid+extension;
        String savePath = filePath+saveFileName;
        
        System.out.println("파일 저장 시도: " + savePath);
        
        try {
            file.transferTo(new File(savePath));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        String url = fileUrl+saveFileName;
        System.out.println("반환 URL: " + url);
        return url;
    }
    public Resource getImage(String fileName){
        Resource resource = null;

        try{
                resource = new UrlResource("file:"+filePath+fileName);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return resource;
    }
}
