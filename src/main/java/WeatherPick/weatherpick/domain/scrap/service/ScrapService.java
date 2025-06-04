package WeatherPick.weatherpick.domain.scrap.service;

import WeatherPick.weatherpick.domain.review.entity.ReviewPostEntity;
import WeatherPick.weatherpick.domain.review.repository.ReviewPostRepository;
import WeatherPick.weatherpick.domain.scrap.DTO.ScrapRequestDTO;
import WeatherPick.weatherpick.domain.scrap.DTO.ScrapResponseDTO;
import WeatherPick.weatherpick.domain.scrap.entity.ScrapEntity;
import WeatherPick.weatherpick.domain.scrap.repository.ScrapRepository;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import WeatherPick.weatherpick.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;
    private final ReviewPostRepository reviewPostRepository;

    public ScrapResponseDTO addScrap(ScrapRequestDTO dto) {
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        ReviewPostEntity post = reviewPostRepository.findById(dto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        scrapRepository.findByUserAndReviewPost(user, post).ifPresent(scrap -> {
            throw new IllegalArgumentException("이미 스크랩한 게시글입니다.");
        });

        ScrapEntity srap = ScrapEntity.builder().user(user).reviewPost(post).build();
        ScrapEntity saved = scrapRepository.save(srap);

        return ScrapResponseDTO.builder()
                .scrapId(saved.getScrapid()).userId(user.getUserKey())
                .reviewpostId(post.getReviewId()).build();
    }

    public void deleteScrap(Long userId, Long postId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        ReviewPostEntity post = reviewPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        ScrapEntity scrap = scrapRepository.findByUserAndReviewPost(user, post)
                .orElseThrow(() -> new IllegalArgumentException("스크랩이 존재하지 않음"));

        scrapRepository.delete(scrap);
    }

    public List<ScrapResponseDTO> getScrapsByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        return scrapRepository.findByUser(user).stream()
                .map(scrap -> ScrapResponseDTO.builder()
                        .scrapId(scrap.getScrapid())
                        .userId(user.getUserKey())
                        .reviewpostId(scrap.getReviewPost().getReviewId())
                        .build()).collect(Collectors.toList());
    }
}
