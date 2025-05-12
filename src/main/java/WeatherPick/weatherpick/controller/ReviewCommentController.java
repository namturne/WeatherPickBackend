package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.domain.review.dto.ReviewCommentDto;
import WeatherPick.weatherpick.domain.review.service.ReviewCommentService;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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

    private final ReviewCommentService commentService;

    public ReviewCommentController(ReviewCommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 조회: 특정 게시글(postId)에 달린 댓글 목록 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ReviewCommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<ReviewCommentDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    // POST 요청: 특정 게시글(postId)에 새 댓글 등록 (인증된 사용자)
    @PostMapping("/{postId}")
    public ResponseEntity<ReviewCommentDto> addComment(
            @PathVariable Long postId,
            @RequestBody ReviewCommentDto commentDto,
            @AuthenticationPrincipal UserEntity user) {
        ReviewCommentDto savedComment = commentService.addComment(postId, commentDto, user);
        return ResponseEntity.ok(savedComment);
    }

    // PUT 요청: 특정 댓글(commentId) 수정 (작성자 본인만 가능)
    @PutMapping("/{commentId}")
    public ResponseEntity<ReviewCommentDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody ReviewCommentDto commentDto,
            @AuthenticationPrincipal UserEntity user) {
        ReviewCommentDto updatedComment = commentService.updateComment(commentId, commentDto, user);
        return ResponseEntity.ok(updatedComment);
    }

    // DELETE 요청: 특정 댓글(commentId) 삭제 (작성자 본인만 가능)
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserEntity user) {
        commentService.deleteComment(commentId, user);
        return ResponseEntity.ok().build();
    }
}
