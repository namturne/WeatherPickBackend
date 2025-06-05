package WeatherPick.weatherpick.domain.review.dto;

import WeatherPick.weatherpick.common.ResponseCode;
import WeatherPick.weatherpick.common.ResponseDto;
import WeatherPick.weatherpick.common.ResponseMassage;
import WeatherPick.weatherpick.domain.place.dto.NPlaceDto;
import WeatherPick.weatherpick.domain.place.entity.NaverPlaceEntity;
import WeatherPick.weatherpick.domain.review.repository.GetReviewPostResultSet;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class GetReviewResponseDto extends ResponseDto {

    private Long id;
    private String title;
    private String content;
    private String writeDate;
    private String writerNickname;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private List<NPlaceDto> places;

    private GetReviewResponseDto(GetReviewPostResultSet resultSet, List<NaverPlaceEntity> naverPlaceEntities) {
        super(ResponseCode.SUCCESS, ResponseCode.SUCCESS);
        this.id = resultSet.getReviewId();
        this.title = resultSet.getTitle();
        this.content = resultSet.getContent();
        this.writeDate = resultSet.getWriteDate();
        this.writerNickname = resultSet.getWriterNickname();
        this.viewCount = resultSet.getViewCount();
        this.likeCount = resultSet.getLikeCount();
        this.commentCount = resultSet.getCommentCount();
        this.places = naverPlaceEntities.stream()
                .map(NPlaceDto::from)
                .toList();
    }

    public static ResponseEntity<GetReviewResponseDto> success(GetReviewPostResultSet resultSet, List<NaverPlaceEntity> naverPlaceEntities) {
        GetReviewResponseDto result = new GetReviewResponseDto(resultSet, naverPlaceEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> notExistReview() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXIDSTED_REVIEW, ResponseMassage.NOT_EXIDSTED_REVIEW);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
