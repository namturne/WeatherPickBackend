package WeatherPick.weatherpick.domain.review.repository;

import WeatherPick.weatherpick.domain.review.entity.FavoritePk;
import WeatherPick.weatherpick.domain.review.entity.ReviewFavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FavoriteRepository extends JpaRepository<ReviewFavoriteEntity, FavoritePk> {
    ReviewFavoriteEntity findByReviewIdAndUsername(Long ReviewId,String username);
}
