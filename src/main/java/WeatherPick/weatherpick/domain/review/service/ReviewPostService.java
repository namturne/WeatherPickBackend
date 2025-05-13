package WeatherPick.weatherpick.domain.review.service;

import WeatherPick.weatherpick.domain.review.dto.ReviewPostDto;
import WeatherPick.weatherpick.domain.review.entity.ReviewPostEntity;
import WeatherPick.weatherpick.domain.review.repository.ReviewPostRepository;
import WeatherPick.weatherpick.domain.review.repository.ReviewRatingRepository;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import WeatherPick.weatherpick.domain.user.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewPostService {
    private final ReviewPostRepository postRepo;
    private final ReviewRatingRepository ratingRepo;
    private final UserRepository userRepo;

    public ReviewPostService(ReviewPostRepository postRepo,
                             ReviewRatingRepository ratingRepo,
                             UserRepository userRepo) {
        this.postRepo = postRepo;
        this.ratingRepo = ratingRepo;
        this.userRepo = userRepo;
    }

    private ReviewPostDto toDto(ReviewPostEntity e) {
        return new ReviewPostDto(
                e.getId(), e.getTitle(), e.getContent(),
                e.getRating(), e.getScrapCount(), e.getCreatedDate()
        );
    }

    // 내 게시글 조회
    @Transactional(readOnly = true)
    public List<ReviewPostDto> getMyPosts(String username) {
        UserEntity me = userRepo.findByUsername(username).orElseThrow();
        return postRepo.findAllByUser_UserKey(me.getUserKey())
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    // 내가 스크랩한 글 조회
    @Transactional(readOnly = true)
    public List<ReviewPostDto> getMyScraps(String username) {
        UserEntity me = userRepo.findByUsername(username).orElseThrow();
        return ratingRepo.findByUser_UserKeyAndScrapedTrue(me.getUserKey())
                .stream().map(r -> toDto(r.getPost())).collect(Collectors.toList());
    }

    // 새 게시글 생성
    @Transactional
    public ReviewPostDto createPost(ReviewPostDto dto, UserEntity user) {
        ReviewPostEntity e = new ReviewPostEntity();
        e.setTitle(dto.getTitle());
        e.setContent(dto.getContent());
        e.setRating(dto.getRating());
        e.setUser(user);
        ReviewPostEntity saved = postRepo.save(e);
        return toDto(saved);
    }

    // 게시글 수정
    @Transactional
    public ReviewPostDto updatePost(Long postId, ReviewPostDto dto, UserEntity user) {
        ReviewPostEntity e = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));
        if (!e.getUser().getUserKey().equals(user.getUserKey())) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }
        e.setTitle(dto.getTitle());
        e.setContent(dto.getContent());
        e.setRating(dto.getRating());
        return toDto(postRepo.save(e));
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, UserEntity user) {
        ReviewPostEntity e = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));
        if (!e.getUser().getUserKey().equals(user.getUserKey())) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }
        postRepo.delete(e);
    }
}
