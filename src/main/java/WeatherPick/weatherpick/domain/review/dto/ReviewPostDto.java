package WeatherPick.weatherpick.domain.review.dto;

import WeatherPick.weatherpick.common.ResponseCode;
import WeatherPick.weatherpick.common.ResponseDto;
import WeatherPick.weatherpick.common.ResponseMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewPostDto extends ResponseDto {

    private Long   id;            // 게시글 고유 ID (ReviewPostEntity.reviewId)
    private String writer;        // 작성자 username
    private String writingTime;   // 작성 시간 (생성일자)
    private String title;
    private String body;          // 기존의 'content' → 프론트에서 body
    private List<String> placeList;


    public ReviewPostDto(Long id,
                         String title,
                         String body,
                         String writer,
                         String writingTime,
                         List<String> placeList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.id          = id;
        this.title       = title;
        this.body        = body;
        this.writer      = writer;
        this.writingTime = writingTime;
        this.placeList   = placeList;
    }

    public ReviewPostDto(String title, String body, List<String> placeList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.title     = title;
        this.body      = body;
        this.placeList = placeList;
    }

    public static ResponseEntity<ReviewPostDto> success(){
        ReviewPostDto result = new ReviewPostDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> notExistUser(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXIDSTED_USER,ResponseMessage.NOT_EXIDSTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
