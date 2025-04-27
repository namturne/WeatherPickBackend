package WeatherPick.weatherpick.domain.review.service;

import WeatherPick.weatherpick.domain.review.dto.ReviewCommentDto;
import WeatherPick.weatherpick.domain.review.entity.ReviewCommentEntity;
import WeatherPick.weatherpick.domain.review.entity.ReviewPostEntity;
import WeatherPick.weatherpick.domain.review.repository.ReviewCommentRepository;
import WeatherPick.weatherpick.domain.review.repository.ReviewPostRepository;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import WeatherPick.weatherpick.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
댓글 (등록, 수정, 삭제)
*/

@Service
public class ReviewCommentService {

    private final ReviewCommentRepository commentRepository;
    private final ReviewPostRepository postRepository;
    private final UserRepository userRepository;

    public ReviewCommentService(ReviewCommentRepository commentRepository,
                                ReviewPostRepository postRepository,
                                UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 댓글 등록: 특정 리뷰 게시글에 댓글 추가
    public ReviewCommentDto addComment(Long postId, ReviewCommentDto commentDto, UserEntity user) {
        ReviewPostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        ReviewCommentEntity comment = new ReviewCommentEntity();
        comment.setPost(post);
        comment.setUser(user);
        comment.setReview_comment_content(commentDto.getReview_comment_content());

        ReviewCommentEntity savedComment = commentRepository.save(comment);
        return new ReviewCommentDto(
                savedComment.getReview_comment_id(),
                savedComment.getUser().getUsername(),
                savedComment.getReview_comment_content(),
                savedComment.getReview_comment_createdAt()
        );
    }


    // 댓글 수정: 댓글 작성자와 인증된 유저가 일치하는 경우에만 수정 가능
    public ReviewCommentDto updateComment(Long commentId, ReviewCommentDto commentDto, UserEntity user) {
        ReviewCommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));
        if (!comment.getUser().getUser_key().equals(user.getUser_key())) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }
        comment.setReview_comment_content(commentDto.getReview_comment_content());
        ReviewCommentEntity updatedComment = commentRepository.save(comment);
        return new ReviewCommentDto(
                updatedComment.getReview_comment_id(),
                updatedComment.getUser().getUsername(),
                updatedComment.getReview_comment_content(),
                updatedComment.getReview_comment_createdAt()
        );
    }

    // 댓글 삭제: 댓글 작성자와 인증된 유저가 일치하는 경우에만 삭제 가능
    public void deleteComment(Long commentId, UserEntity user) {
        ReviewCommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));
        if (!comment.getUser().getUser_key().equals(user.getUser_key())) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        commentRepository.delete(comment);
    }
}
