package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.domain.review.dto.ReviewPostDto;
import WeatherPick.weatherpick.domain.review.service.ReviewPostService;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/posts")
public class ReviewPostController {
    private final ReviewPostService service;

    public ReviewPostController(ReviewPostService service) {
        this.service = service;
    }

    @GetMapping("/mine")
    public ResponseEntity<?> getMyPosts(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(service.getMyPosts(user.getUsername()));
    }

    @GetMapping("/scraps")
    public ResponseEntity<?> getMyScraps(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(service.getMyScraps(user.getUsername()));
    }

    @PostMapping
    public ResponseEntity<ReviewPostDto> createPost(
            @RequestBody ReviewPostDto dto,
            @AuthenticationPrincipal UserEntity user) {
        ReviewPostDto saved = service.createPost(dto, user);
        return ResponseEntity.created(URI.create("/api/posts/" + saved.getId())).body(saved);
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
