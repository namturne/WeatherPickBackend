package WeatherPick.weatherpick.domain.review.service;

import WeatherPick.weatherpick.common.ResponseDto;
import WeatherPick.weatherpick.domain.place.entity.NaverPlaceEntity;
import WeatherPick.weatherpick.domain.place.repository.NaverPlaceRepository;
import WeatherPick.weatherpick.domain.review.dto.*;
import WeatherPick.weatherpick.domain.review.entity.ReviewCommentEntity;
import WeatherPick.weatherpick.domain.review.entity.ReviewFavoriteEntity;
import WeatherPick.weatherpick.domain.review.entity.ReviewPostEntity;
import WeatherPick.weatherpick.domain.review.entity.ReviewScrapEntity;
import WeatherPick.weatherpick.domain.review.repository.*;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import WeatherPick.weatherpick.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewPostService {
    private final ReviewPostRepository postRepo;
    private final UserRepository userRepo;
    private final FavoriteRepository faRepo;
    private final NaverPlaceRepository naverPlaceRepo;
    private final ScrapRepository scRepo;
    private final ReviewCommentRepository coRepo;

    private ReviewPostDto toDto(ReviewPostEntity e) {
        return new ReviewPostDto(
                e.getTitle(), e.getContent(),null
        );
    }

    //선택한 게시글 조회
    public ResponseEntity<? super GetReviewResponseDto> getReview(Long ReviewPostId){
            GetReviewPostResultSet resultSet = null;
            List<NaverPlaceEntity> naverPlaceEntities = new ArrayList<>();
        try{
            resultSet = postRepo.getReview(ReviewPostId);
            if(resultSet == null) return  GetReviewResponseDto.notExistReview();
            naverPlaceEntities = naverPlaceRepo.findByReview_ReviewPostId(ReviewPostId);

            ReviewPostEntity reviewPostEntity = postRepo.findByReviewPostId(ReviewPostId);
            reviewPostEntity.increaseViewCount();
            postRepo.save(reviewPostEntity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetReviewResponseDto.success(resultSet,naverPlaceEntities);
    }


    // 내 게시글 조회
    @Transactional(readOnly = true)
    public List<ReviewPostDto> getMyPosts(String username) {
        UserEntity me = userRepo.findByUsername(username).orElseThrow();
        return postRepo.findAllByUser_UserId(me.getUserId())
                .stream().map(this::toDto).collect(Collectors.toList());
    }



    // 최신 게시글 리스트 조회
    public ResponseEntity<? super GetReviewListResponseDto> getReviewList(){
        List<ReviewPostEntity> reviewPostEntities;
        try {

            reviewPostEntities = postRepo.findByOrderByWriteDateDesc();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetReviewListResponseDto.success(reviewPostEntities);
    }

    // 새 게시글 생성
    @Transactional
    public ResponseEntity<? super ReviewPostDto> createPost(ReviewPostRequestDto dto, String username) {
        try {
            boolean existedUsername = userRepo.existsByUsername(username);
            if (!existedUsername) return ReviewPostDto.notExistUser();

            // 장소 유효성 검사
            if (dto.getPlaces() != null) {
                for (ReviewPostRequestDto.PlaceDto place : dto.getPlaces()) {
                    if (!StringUtils.hasText(place.getTitle()) ||
                        !StringUtils.hasText(place.getAddress()) ||
                        !StringUtils.hasText(place.getRoadAddress()) ||
                        !StringUtils.hasText(place.getMapx()) ||
                        !StringUtils.hasText(place.getMapy())) {
                        return ResponseDto.databaseError();
                    }
                }
            }

            ReviewPostEntity post = new ReviewPostEntity();
            post.setTitle(dto.getTitle());
            post.setContent(dto.getContent());
            post.setUser(userRepo.findByUsername(username).get());
            
            // 게시글 저장
            ReviewPostEntity savedPost = postRepo.save(post);

            // 장소 정보 저장
            if (dto.getPlaces() != null) {
                List<NaverPlaceEntity> places = new ArrayList<>();
                for (ReviewPostRequestDto.PlaceDto placeDto : dto.getPlaces()) {
                    NaverPlaceEntity place = new NaverPlaceEntity();
                    place.setTitle(placeDto.getTitle());
                    place.setAddress(placeDto.getAddress());
                    place.setRoadAddress(placeDto.getRoadAddress());
                    place.setMapx(placeDto.getMapx());
                    place.setMapy(placeDto.getMapy());
                    place.setCategory(placeDto.getCategory());
                    place.setLink(placeDto.getLink());
                    place.setReview(savedPost);
                    places.add(place);
                }
                naverPlaceRepo.saveAll(places);
            }

            return ReviewPostDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    // 게시글 수정
    @Transactional
    public ReviewPostDto updatePost(Long postId, ReviewPostDto dto, UserEntity user) {
        ReviewPostEntity e = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));
        if (!e.getUser().getUserId().equals(user.getUserId())) {
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
        if (!e.getUser().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }
        postRepo.delete(e);
    }
    //좋아요
    public  ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Long ReviewPostId,String username){
        try {
            boolean existedUser = userRepo.existsByUsername(username);
            if(!existedUser) return PutFavoriteResponseDto.noExistUser();
            ReviewPostEntity reviewPostEntity = postRepo.findByReviewPostId(ReviewPostId);
            if(reviewPostEntity==null) return PutFavoriteResponseDto.noExistReview();


            ReviewFavoriteEntity reviewFavoriteEntity = faRepo.findByReviewPostIdAndUsername(ReviewPostId,username);
            if(reviewFavoriteEntity==null){
                reviewFavoriteEntity = new ReviewFavoriteEntity(username,ReviewPostId);
                faRepo.save(reviewFavoriteEntity);
                reviewPostEntity.increaseFavoriteCount();
            }
            else {
                faRepo.delete(reviewFavoriteEntity);
                reviewPostEntity.decreaseFavoriteCount();
            }
            postRepo.save(reviewPostEntity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PutFavoriteResponseDto.success();
    }
    //스크랩
    public  ResponseEntity<? super PutScrapResponseDto> putScrap(Long ReviewPostId,String username){
        try {
            boolean existedUser = userRepo.existsByUsername(username);
            if(!existedUser) return PutScrapResponseDto.noExistUser();
            ReviewPostEntity reviewPostEntity = postRepo.findByReviewPostId(ReviewPostId);
            if(reviewPostEntity==null) return PutScrapResponseDto.noExistReview();


            ReviewScrapEntity reviewScrapEntity = scRepo.findByReviewPostIdAndUsername(ReviewPostId,username);
            if(reviewScrapEntity==null){
                reviewScrapEntity = new ReviewScrapEntity(username,ReviewPostId);
                scRepo.save(reviewScrapEntity);
                reviewPostEntity.increaseScrapCount();
            }
            else {
                scRepo.delete(reviewScrapEntity);
                reviewPostEntity.decreaseScrapCount();
            }
            postRepo.save(reviewPostEntity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PutScrapResponseDto.success();
    }

    //댓글작성
    public ResponseEntity<? super ReviewCommentResponseDto> createComment(ReviewCommentRequestDto dto, Long ReviewPostId, String username){
        try {
            ReviewPostEntity reviewPostEntity = postRepo.findByReviewPostId(ReviewPostId);
            if(reviewPostEntity==null) return ReviewCommentResponseDto.noExistReview();
            boolean existedUser = userRepo.existsByUsername(username);
            if(!existedUser) return ReviewCommentResponseDto.noExistUser();

            ReviewCommentEntity comment = new ReviewCommentEntity();
            comment.setContent(dto.getContent());
            comment.setReviewPostId(reviewPostEntity);
            comment.setUserId(userRepo.findByUsername(username).get());
            coRepo.save(comment);

            reviewPostEntity.increaseCommentCount();
            postRepo.save(reviewPostEntity);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ReviewCommentResponseDto.success();
    }
    //댓글조회
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Long ReviewPostId){
        List<GetReviewCommentResultSet> resultSets = new ArrayList<>();
        try {
            boolean existedReview = postRepo.existsById(ReviewPostId);
            if(!existedReview) return GetCommentListResponseDto.noExistReview();

            resultSets = coRepo.getCommentList(ReviewPostId);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetCommentListResponseDto.success(resultSets);
    }
}
