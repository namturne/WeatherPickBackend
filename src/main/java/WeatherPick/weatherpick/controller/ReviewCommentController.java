package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.domain.review.dto.ReviewCommentDto;
import WeatherPick.weatherpick.domain.review.service.ReviewCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class ReviewCommentController {

    private final ReviewCommentService service;

    public ReviewCommentController(ReviewCommentService service) {
        this.service = service;
    }

    // 1) 특정 게시글의 댓글 전체 조회 → 공개
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ReviewCommentDto>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(service.getCommentsByPostId(postId));
    }

    // 2) 내가 쓴 댓글 조회 → 인증 필요
    @GetMapping("/me")
    public ResponseEntity<List<ReviewCommentDto>> getMyComments(
            @AuthenticationPrincipal String username
    ) {
        if (username == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(service.getCommentsByUsername(username));
    }

    // 3) 댓글 등록 → 로그인된 사용자만 가능
    @PostMapping("/{postId}")
    public ResponseEntity<ReviewCommentDto> addComment(
            @PathVariable Long postId,
            @RequestBody ReviewCommentDto dto,
            @AuthenticationPrincipal String username
    ) {
        if (username == null) {
            return ResponseEntity.status(401).build();
        }
        ReviewCommentDto saved = service.addComment(postId, dto, username);
        return ResponseEntity
                .created(URI.create("/api/comments/" + saved.getId()))
                .body(saved);
    }

    // 4) 댓글 수정 → 본인만 가능
    @PutMapping("/{commentId}")
    public ResponseEntity<ReviewCommentDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody ReviewCommentDto dto,
            @AuthenticationPrincipal String username
    ) {
        if (username == null) {
            return ResponseEntity.status(401).build();
        }
        try {
            return ResponseEntity.ok(service.updateComment(commentId, dto, username));
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(403).build();
        }
    }

    // 5) 댓글 삭제 → 본인만 가능
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal String username
    ) {
        if (username == null) {
            return ResponseEntity.status(401).build();
        }
        try {
            service.deleteComment(commentId, username);
            return ResponseEntity.noContent().build();
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(403).build();
        }
    }
}
