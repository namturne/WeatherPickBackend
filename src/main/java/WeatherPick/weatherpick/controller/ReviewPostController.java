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



    @PostMapping("")
    public ResponseEntity<? super ReviewPostDto> createPost(
            @RequestBody @Valid ReviewPostRequestDto dto,
            @AuthenticationPrincipal String username) {
        //ReviewPostDto saved = service.createPost(dto, username);
        //return ResponseEntity.created(URI.create("/api/posts/" + saved.getId())).body(saved);
        return service.createPost(dto,username);
    }
    @GetMapping("/list")
    public ResponseEntity<? super GetReviewListResponseDto> getReviewList(){
        return service.getReviewList();
    }


    @PutMapping("/{postId}/favorite")
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(
            @PathVariable("postId") Long reviewId,
            @AuthenticationPrincipal String username
    ){
        return service.putFavorite(reviewId,username);
    }

    @PutMapping("/{postId}/scrap")
    public ResponseEntity<? super PutScrapResponseDto> putscrap(
            @PathVariable("postId") Long reviewId,
            @AuthenticationPrincipal String username
    ){
        return service.putScrap(reviewId,username);
    }


    @GetMapping("/{postId}")
    public ResponseEntity<? super GetReviewResponseDto> getPost(
            @PathVariable("postId") Long ReviewId
    ){
        return service.getReview(ReviewId);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ReviewPostDto> updatePost(
            @PathVariable Long postId,
            @RequestBody ReviewPostDto dto,
            @AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(service.updatePost(postId, dto, user));
    }



    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserEntity user) {
        service.deletePost(postId, user);
        return ResponseEntity.noContent().build();
    }
}
