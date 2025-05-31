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
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

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

    // 1) 게시글 상세 조회
    public ResponseEntity<? super GetReviewResponseDto> getReview(Long reviewId) {
        GetReviewPostResultSet resultSet = null;
        List<TestplaceEntity> testplaceEntities = new ArrayList<>();
        try {
            resultSet = postRepo.getReview(reviewId);
            if (resultSet == null) {
                return GetReviewResponseDto.notExistReview();
            }
            testplaceEntities = plRepo.findByReviewId(reviewId);

            // 조회수 증가
            ReviewPostEntity reviewPostEntity = postRepo.findByReviewId(reviewId);
            reviewPostEntity.increaseViewCount();
            postRepo.save(reviewPostEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetReviewResponseDto.success(resultSet, testplaceEntities);
    }

    // 2) 새 게시글 생성
    @Transactional
    public ResponseEntity<? super ReviewPostDto> createPost(@Valid ReviewPostRequestDto dto,
                                                            String username) {
        try {
            // 사용자 확인
            boolean existedUsername = userRepo.existsByUsername(username);
            if (!existedUsername) {
                return ReviewPostDto.notExistUser();
            }

            // 게시글 저장
            ReviewPostEntity e = new ReviewPostEntity();
            e.setTitle(dto.getTitle());
            e.setContent(dto.getContent());
            UserEntity me = userRepo.findByUsername(username).get();
            e.setUser(me);
            ReviewPostEntity saved = postRepo.save(e);

            // 장소 리스트 저장 (TestplaceEntity)
            Long reviewId = saved.getReviewId();
            List<String> placeList = dto.getPlaceList();
            List<TestplaceEntity> testplaceEntities = new ArrayList<>();
            if (placeList != null) {
                for (String place : placeList) {
                    TestplaceEntity t = new TestplaceEntity(reviewId, place);
                    testplaceEntities.add(t);
                }
                plRepo.saveAll(testplaceEntities);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ReviewPostDto.success();
    }

    // 3) 내가 쓴 게시글 조회
    @Transactional(Transactional.TxType.REQUIRED)
    public List<ReviewPostDto> getMyPosts(String username) {
        UserEntity me = userRepo.findByUsername(username).orElseThrow();
        List<ReviewPostEntity> entityList = postRepo.findAllByUser_UserKey(me.getUserKey());

        return entityList.stream().map(e -> {
            Long id            = e.getReviewId();
            String title       = e.getTitle();
            String body        = e.getContent();
            String writer      = e.getUser().getUsername();
            String writingTime = e.getCreatedDate();
            List<String> places = null;
            return new ReviewPostDto(id, title, body, writer, writingTime, places);
        }).collect(Collectors.toList());
    }

    // 4) 내가 스크랩한 게시글 조회
    @Transactional(Transactional.TxType.REQUIRED)
    public List<ReviewPostDto> getMyScraps(String username) {
        UserEntity me = userRepo.findByUsername(username).orElseThrow();
        return ratingRepo.findByUser_UserKeyAndScrapedTrue(me.getUserKey())
                .stream()
                .map(r -> {
                    ReviewPostEntity e = r.getPost();
                    Long id = e.getReviewId();
                    String title = e.getTitle();
                    String body = e.getContent();
                    String writer = e.getUser().getUsername();
                    String writingTime = e.getCreatedDate();
                    List<String> places = null;
                    return new ReviewPostDto(id, title, body, writer, writingTime, places);
                })
                .collect(Collectors.toList());
    }

    // 5) 게시글 수정
    @Transactional
    public ReviewPostDto updatePost(Long postId,
                                    ReviewPostDto dto,
                                    String username) {
        UserEntity me = userRepo.findByUsername(username).orElseThrow();
        ReviewPostEntity e = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));
        if (!e.getUser().getUserKey().equals(me.getUserKey())) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }

        e.setTitle(dto.getTitle());
        e.setContent(dto.getBody());
        ReviewPostEntity updated = postRepo.save(e);

        return new ReviewPostDto(
                updated.getReviewId(),
                updated.getTitle(),
                updated.getContent(),
                updated.getUser().getUsername(),
                updated.getCreatedDate(),
                null
        );
    }

    // 6) 게시글 삭제
    @Transactional
    public void deletePost(Long postId, String username) {
        UserEntity me = userRepo.findByUsername(username).orElseThrow();
        ReviewPostEntity e = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));
        if (!e.getUser().getUserKey().equals(me.getUserKey())) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }
        postRepo.delete(e);
    }
}
