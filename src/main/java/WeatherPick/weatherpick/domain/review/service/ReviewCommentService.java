package WeatherPick.weatherpick.domain.review.service;

import WeatherPick.weatherpick.domain.review.dto.ReviewCommentDto;
import WeatherPick.weatherpick.domain.review.entity.ReviewCommentEntity;
import WeatherPick.weatherpick.domain.review.entity.ReviewPostEntity;
import WeatherPick.weatherpick.domain.review.repository.ReviewCommentRepository;
import WeatherPick.weatherpick.domain.review.repository.ReviewPostRepository;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import WeatherPick.weatherpick.domain.user.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewCommentService {

    private final ReviewCommentRepository commentRepo;
    private final ReviewPostRepository postRepo;
    private final UserRepository userRepo;

    public ReviewCommentService(
            ReviewCommentRepository commentRepo,
            ReviewPostRepository postRepo,
            UserRepository userRepo
    ) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    private ReviewCommentDto toDto(ReviewCommentEntity e) {
        return new ReviewCommentDto(
                e.getId(),
                e.getUser().getUsername(),
                e.getContent(),
                e.getCreatedDate()
        );
    }

    // 댓글 조회
    @Transactional(readOnly = true)
    public List<ReviewCommentDto> getCommentsByPostId(Long postId) {
        return commentRepo.findByPost_ReviewId(postId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ▶ 내가 쓴 댓글 조회 (username 문자열로 받음)
    @Transactional(readOnly = true)
    public List<ReviewCommentDto> getCommentsByUsername(String username) {
        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
        return commentRepo.findByUser_UserKey(user.getUserKey())
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 댓글 등록
    @Transactional
    public ReviewCommentDto addComment(Long postId, ReviewCommentDto dto, String username) {
        ReviewPostEntity post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));
        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));

        ReviewCommentEntity c = new ReviewCommentEntity();
        c.setPost(post);
        c.setUser(user);
        c.setContent(dto.getContent());
        return toDto(commentRepo.save(c));
    }

    // 댓글 수정
    @Transactional
    public ReviewCommentDto updateComment(Long id, ReviewCommentDto dto, String username) {
        ReviewCommentEntity c = commentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 없습니다."));
        if (!c.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }
        c.setContent(dto.getContent());
        return toDto(commentRepo.save(c));
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long id, String username) {
        ReviewCommentEntity c = commentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 없습니다."));
        if (!c.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }
        commentRepo.delete(c);
    }
}
