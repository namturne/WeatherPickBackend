package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.domain.review.dto.GetReviewResponseDto;
import WeatherPick.weatherpick.domain.review.dto.ReviewPostDto;
import WeatherPick.weatherpick.domain.review.dto.ReviewPostRequestDto;
import WeatherPick.weatherpick.domain.review.service.ReviewPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class ReviewPostController {
    private final ReviewPostService service;

    // 1) 나의 게시글 목록 조회
    @GetMapping("/mine")
    public ResponseEntity<List<ReviewPostDto>> getMyPosts(
            @AuthenticationPrincipal String username) {
        List<ReviewPostDto> list = service.getMyPosts(username);
        return ResponseEntity.ok(list);
    }

    // 2) 내가 스크랩한 게시글 목록 조회
    @GetMapping("/scraps")
    public ResponseEntity<List<ReviewPostDto>> getMyScraps(
            @AuthenticationPrincipal String username) {
        List<ReviewPostDto> list = service.getMyScraps(username);
        return ResponseEntity.ok(list);
    }

    // 3) 새 게시글 생성
    @PostMapping("")
    public ResponseEntity<? super ReviewPostDto> createPost(
            @RequestBody @Valid ReviewPostRequestDto dto,
            @AuthenticationPrincipal String username) {
        return service.createPost(dto, username);
    }

    // 4) 특정 게시글 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity<? super GetReviewResponseDto> getPost(
            @PathVariable("postId") Long reviewId) {
        return service.getReview(reviewId);
    }

    // 5) 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<ReviewPostDto> updatePost(
            @PathVariable Long postId,
            @RequestBody ReviewPostDto dto,
            @AuthenticationPrincipal String username) {
        ReviewPostDto updated = service.updatePost(postId, dto, username);
        return ResponseEntity.ok(updated);
    }

    // 6) 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal String username) {
        service.deletePost(postId, username);
        return ResponseEntity.noContent().build();
    }
}
