package WeatherPick.weatherpick.domain.review.dto;

import WeatherPick.weatherpick.common.ResponseCode;
import WeatherPick.weatherpick.common.ResponseDto;
import WeatherPick.weatherpick.common.ResponseMassage;
import WeatherPick.weatherpick.domain.review.entity.TestplaceEntity;
import WeatherPick.weatherpick.domain.review.repository.GetReviewPostResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
public class GetReviewResponseDto extends ResponseDto {

    private Long id;
    private String title;
    private String content;
    private List<String> placeList;
    private String createDate;
    private String writerUsername;
    private String writerNickname;
    private Integer likeCount;
    private Integer scrapCount;
    private Integer viewCount;
    private Integer commentCount;


    private GetReviewResponseDto(GetReviewPostResultSet resultSet, List<TestplaceEntity> testplaceEntities){
        super(ResponseCode.SUCCESS,ResponseCode.SUCCESS);

        List<String> placeList = new ArrayList<>();
        for(TestplaceEntity testplaceEntity: testplaceEntities){
            String placeEntity = testplaceEntity.getPlace();
            placeList.add(placeEntity);
        }
        this.id = resultSet.getReviewId();
        this.title = resultSet.getTitle();
        this.content = resultSet.getContent();
        this.placeList =placeList;
        this.createDate = resultSet.getWriteDate();
        this.writerNickname = resultSet.getWriterNickname();
        this.writerUsername = resultSet.getWriterUsername();
        this.likeCount = Optional.ofNullable(resultSet.getLikeCount()).orElse(0);
        this.scrapCount = Optional.ofNullable(resultSet.getScrapCount()).orElse(0);
        this.viewCount= Optional.ofNullable(resultSet.getViewCount()).orElse(0);
        this.commentCount= Optional.ofNullable(resultSet.getCommentCount()).orElse(0);

    }
    public static ResponseEntity<GetReviewResponseDto> success(GetReviewPostResultSet resultSet, List<TestplaceEntity> testplaceEntities){
        GetReviewResponseDto result = new GetReviewResponseDto(resultSet,testplaceEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDto> notExistReview(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXIDSTED_REVIEW, ResponseMassage.NOT_EXIDSTED_REVIEW);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }


}
