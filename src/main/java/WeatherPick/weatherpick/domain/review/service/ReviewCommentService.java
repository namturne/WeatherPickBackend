package WeatherPick.weatherpick.domain.review.service;

import WeatherPick.weatherpick.domain.review.dto.ReviewCommentDto;
import WeatherPick.weatherpick.domain.review.entity.ReviewCommentEntity;
import WeatherPick.weatherpick.domain.review.entity.ReviewPostEntity;
import WeatherPick.weatherpick.domain.review.repository.ReviewCommentRepository;
import WeatherPick.weatherpick.domain.review.repository.ReviewPostRepository;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


/*
댓글 (등록, 수정, 삭제, 조회)
*/

@Service
public class ReviewCommentService {
    private final ReviewCommentRepository commentRepo;
    private final ReviewPostRepository postRepo;

    public ReviewCommentService(ReviewCommentRepository commentRepo,
                                ReviewPostRepository postRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    private ReviewCommentDto toDto(ReviewCommentEntity e) {
        return new ReviewCommentDto(
                e.getId(), e.getUser().getUsername(),
                e.getContent(), e.getCreatedDate()
        );
    }

    // 댓글 조회
    @Transactional(readOnly = true)
    public List<ReviewCommentDto> getCommentsByPostId(Long postId) {
        return commentRepo.findByPost_ReviewId(postId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    // ▶ 내가 쓴 댓글 조회
    @Transactional(readOnly = true)
    public List<ReviewCommentDto> getCommentsByUser(UserEntity user) {
        return commentRepo.findByUser_UserKey(user.getUserKey())
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 댓글 등록
    @Transactional
    public ReviewCommentDto addComment(Long postId, ReviewCommentDto dto, UserEntity user) {
        ReviewPostEntity post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));
        ReviewCommentEntity c = new ReviewCommentEntity();
        c.setPost(post);
        c.setUser(user);
        c.setContent(dto.getContent());
        return toDto(commentRepo.save(c));
    }

    // 댓글 수정
    @Transactional
    public ReviewCommentDto updateComment(Long id, ReviewCommentDto dto, UserEntity user) {
        ReviewCommentEntity c = commentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글이 없습니다."));
        if (!c.getUser().getUserKey().equals(user.getUserKey())) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }
        c.setContent(dto.getContent());
        return toDto(commentRepo.save(c));
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long id, UserEntity user) {
        ReviewCommentEntity c = commentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글이 없습니다."));
        if (!c.getUser().getUserKey().equals(user.getUserKey())) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }
        commentRepo.delete(c);
    }
}
