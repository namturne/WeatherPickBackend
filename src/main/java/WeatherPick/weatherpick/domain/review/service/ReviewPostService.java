package WeatherPick.weatherpick.domain.review.service;

import WeatherPick.weatherpick.domain.review.entity.ReviewPostEntity;
import WeatherPick.weatherpick.domain.review.repository.ReviewPostRepository;
import WeatherPick.weatherpick.domain.review.repository.ReviewRatingRepository;
import WeatherPick.weatherpick.domain.review.dto.ReviewPostDto;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import WeatherPick.weatherpick.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
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

    /** 내가 쓴 글 조회 */
    public List<ReviewPostDto> getMyPosts(String username) {
        UserEntity me = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저가 없어요"));
        List<ReviewPostEntity> entities = postRepo.findAllByUserKey(me.getUser_key());
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /** 내가 스크랩한 글 조회 */
    public List<ReviewPostDto> getMyScraps(String username) {
        UserEntity me = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저가 없어요"));
        List<ReviewPostEntity> scraps = ratingRepo.findScrappedPostsByUser(me.getUser_key());
        return scraps.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /** ReviewPostEntity → DTO 변환 헬퍼 메서드 */
    private ReviewPostDto toDto(ReviewPostEntity e) {
        return new ReviewPostDto(
                e.getreviewpost_id(),
                e.getreviewposttitle(),
                e.getreviewpostcontent(),
                e.getRating(),
                e.getScrapCnt(),
                e.getCreatedDate()
        );
    }
}
