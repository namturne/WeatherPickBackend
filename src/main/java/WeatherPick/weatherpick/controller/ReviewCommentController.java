package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.domain.review.dto.ReviewCommentDto;
import WeatherPick.weatherpick.domain.review.service.ReviewCommentService;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/*
엔드포인트:
 - POST   /api/comments/{postId}    : 댓글 등록 (로그인된 사용자만 가능)
 - PUT    /api/comments/{commentId} : 댓글 수정 (본인만 수정 가능)
 - DELETE /api/comments/{commentId} : 댓글 삭제 (본인만 삭제 가능)
 - SELECT /api/comments/{postId}    : 댓글 조회 (본인만 조회 가능)
*/

@RestController
@RequestMapping("/api/comments")
public class ReviewCommentController {
    private final ReviewCommentService service;

    public ReviewCommentController(ReviewCommentService service) {
        this.service = service;
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ReviewCommentDto>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(service.getCommentsByPostId(postId));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<ReviewCommentDto> addComment(
            @PathVariable Long postId,
            @RequestBody ReviewCommentDto dto,
            @AuthenticationPrincipal UserEntity user) {
        ReviewCommentDto saved = service.addComment(postId, dto, user);
        return ResponseEntity.created(URI.create("/api/comments/" + saved.getId())).body(saved);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ReviewCommentDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody ReviewCommentDto dto,
            @AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(service.updateComment(commentId, dto, user));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserEntity user) {
        service.deleteComment(commentId, user);
        return ResponseEntity.noContent().build();
    }
}
