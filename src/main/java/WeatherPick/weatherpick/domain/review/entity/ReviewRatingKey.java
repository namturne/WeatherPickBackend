package WeatherPick.weatherpick.domain.review.entity;

import java.io.Serializable;
import java.util.Objects;

//user, post 복합키가 PK라는 것 명시

public class ReviewRatingKey implements Serializable {
    private Long user;
    private Long post;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewRatingKey)) return false;
        ReviewRatingKey that = (ReviewRatingKey) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(post, that.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, post);
    }
}
