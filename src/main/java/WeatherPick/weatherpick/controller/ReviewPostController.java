package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.domain.review.dto.ReviewPostDto;
import WeatherPick.weatherpick.domain.review.service.ReviewPostService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class ReviewPostController {

    private final ReviewPostService postService;

    public ReviewPostController(ReviewPostService postService) {
        this.postService = postService;
    }

    // 내가 쓴 글 목록
    @GetMapping("/mine")
    public ResponseEntity<List<ReviewPostDto>> getMyPosts(
            @AuthenticationPrincipal UserDetails me
    ) {
        return ResponseEntity.ok(postService.getMyPosts(me.getUsername()));
    }

    // 내가 스크랩한 글 목록
    @GetMapping("/scraps")
    public ResponseEntity<List<ReviewPostDto>> getMyScraps(
            @AuthenticationPrincipal UserDetails me
    ) {
        return ResponseEntity.ok(postService.getMyScraps(me.getUsername()));
    }
}
