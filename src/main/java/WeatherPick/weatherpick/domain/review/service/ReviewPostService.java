package WeatherPick.weatherpick.domain.review.service;

import WeatherPick.weatherpick.common.ResponseDto;
import WeatherPick.weatherpick.domain.review.dto.GetReviewResponseDto;
import WeatherPick.weatherpick.domain.review.dto.ReviewPostDto;
import WeatherPick.weatherpick.domain.review.dto.ReviewPostRequestDto;
import WeatherPick.weatherpick.domain.review.entity.ReviewPostEntity;
import WeatherPick.weatherpick.domain.review.entity.TestplaceEntity;
import WeatherPick.weatherpick.domain.review.repository.GetReviewPostResultSet;
import WeatherPick.weatherpick.domain.review.repository.ReviewPostRepository;
import WeatherPick.weatherpick.domain.review.repository.ReviewRatingRepository;
import WeatherPick.weatherpick.domain.review.repository.TestPlaceRepository;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import WeatherPick.weatherpick.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewPostService {
    private final ReviewPostRepository postRepo;
    private final ReviewRatingRepository ratingRepo;
    private final UserRepository userRepo;
    private final TestPlaceRepository plRepo;


    private ReviewPostDto toDto(ReviewPostEntity e) {
        return new ReviewPostDto(
                e.getTitle(), e.getContent(),null
        );
    }

    //선택한 게시글 조회
    public ResponseEntity<? super GetReviewResponseDto> getReview(Long ReviewId){
            GetReviewPostResultSet resultSet = null;
            List<TestplaceEntity> testplaceEntities = new ArrayList<>();
        try{
            resultSet = postRepo.getReview(ReviewId);
            if(resultSet == null) return  GetReviewResponseDto.notExistReview();
            testplaceEntities = plRepo.findByReviewId(ReviewId);

            ReviewPostEntity reviewPostEntity = postRepo.findByReviewId(ReviewId);
            reviewPostEntity.increaseViewCount();
            postRepo.save(reviewPostEntity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetReviewResponseDto.success(resultSet,testplaceEntities);
    }


    // 내 게시글 조회
    @Transactional(readOnly = true)
    public List<ReviewPostDto> getMyPosts(String username) {
        UserEntity me = userRepo.findByUsername(username).orElseThrow();
        return postRepo.findAllByUser_UserKey(me.getUserKey())
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    // 내가 스크랩한 글 조회
    @Transactional(readOnly = true)
    public List<ReviewPostDto> getMyScraps(String username) {
        UserEntity me = userRepo.findByUsername(username).orElseThrow();
        return ratingRepo.findByUser_UserKeyAndScrapedTrue(me.getUserKey())
                .stream().map(r -> toDto(r.getPost())).collect(Collectors.toList());
    }

    // 새 게시글 생성
    @Transactional
    public ResponseEntity<? super ReviewPostDto> createPost(ReviewPostRequestDto dto, String username) {
        try{
            boolean existedUsername = userRepo.existsByUsername(username);

            if(!existedUsername) return ReviewPostDto.notExistUser();

            ReviewPostEntity e = new ReviewPostEntity();
            e.setTitle(dto.getTitle());
            e.setContent(dto.getContent());
            e.setUser(userRepo.findByUsername(username).get());
            ReviewPostEntity saved = postRepo.save(e);

            Long ReviewId = e.getReviewId();
            List<String> ReviewPlaceList = dto.getPlaceList();
            List<TestplaceEntity> testplaceEntities = new ArrayList<>();

            for (String place: ReviewPlaceList){
                TestplaceEntity testplaceEntity = new TestplaceEntity(ReviewId,place);
                testplaceEntities.add(testplaceEntity);
            }

            plRepo.saveAll(testplaceEntities);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }


        return ReviewPostDto.success();
    }

    // 게시글 수정
    @Transactional
    public ReviewPostDto updatePost(Long postId, ReviewPostDto dto, UserEntity user) {
        ReviewPostEntity e = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));
        if (!e.getUser().getUserKey().equals(user.getUserKey())) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }
        e.setTitle(dto.getTitle());
        e.setContent(dto.getContent());
        //e.setRating(dto.getRating());
        return toDto(postRepo.save(e));
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, UserEntity user) {
        ReviewPostEntity e = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));
        if (!e.getUser().getUserKey().equals(user.getUserKey())) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }
        postRepo.delete(e);
    }
}
