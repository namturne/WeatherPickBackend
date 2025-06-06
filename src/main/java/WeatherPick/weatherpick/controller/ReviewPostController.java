package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.domain.review.dto.*;
import WeatherPick.weatherpick.domain.review.service.ReviewPostService;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class ReviewPostController {
    private final ReviewPostService service;

    @GetMapping("/mine")
    public ResponseEntity<?> getMyPosts(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(service.getMyPosts(user.getUsername()));
    }


    //리뷰생성
    @PostMapping("")
    public ResponseEntity<? super ReviewPostDto> createPost(
            @RequestBody @Valid ReviewPostRequestDto dto,
            @AuthenticationPrincipal String username) {
        return service.createPost(dto,username);
    }
    //리뷰목록조회
    @GetMapping("/list")
    public ResponseEntity<? super GetReviewListResponseDto> getReviewList(){
        return service.getReviewList();
    }

    //좋아요
    @PutMapping("/{postId}/favorite")
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(
            @PathVariable("postId") Long reviewId,
            @AuthenticationPrincipal String username
    ){
        return service.putFavorite(reviewId,username);
    }

    //스크랩
    @PutMapping("/{postId}/scrap")
    public ResponseEntity<? super PutScrapResponseDto> putscrap(
            @PathVariable("postId") Long reviewId,
            @AuthenticationPrincipal String username
    ){
        return service.putScrap(reviewId,username);
    }

    //리뷰1개조회
    @GetMapping("/{postId}")
    public ResponseEntity<? super GetReviewResponseDto> getPost(
            @PathVariable("postId") Long ReviewPostId
    ){
        return service.getReview(ReviewPostId);
    }




    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserEntity user) {
        service.deletePost(postId, user);
        return ResponseEntity.noContent().build();
    }

    //댓글
    @PostMapping("/{postId}/comment")
    public ResponseEntity<? super ReviewCommentResponseDto> createComment(
                @RequestBody @Valid ReviewCommentRequestDto requestbody,
                @PathVariable("postId") Long postId,
                @AuthenticationPrincipal String username
    ){
        return service.createComment(requestbody,postId,username);
    }
    @GetMapping("/{postId}/comment-list")
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(
            @PathVariable("postId") Long postId
    ){
        return service.getCommentList(postId);
    }

}
