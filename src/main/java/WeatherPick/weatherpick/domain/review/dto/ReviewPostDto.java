package WeatherPick.weatherpick.domain.review.dto;

import java.util.Date;

public class ReviewPostDto {
    private Long reviewpost_id;
    private String reviewposttitle;
    private String reviewpostcontent;
    private int rating;
    private int scrapCnt;
    private Date createdDate;

    public ReviewPostDto(Long reviewpost_id,
                         String reviewposttitle,
                         String reviewpostcontent,
                         int rating,
                         int scrapCnt,
                         Date createdDate) {
        this.reviewpost_id = reviewpost_id;
        this.reviewposttitle = reviewposttitle;
        this.reviewpostcontent = reviewpostcontent;
        this.rating = rating;
        this.scrapCnt = scrapCnt;
        this.createdDate = createdDate;
    }

    // ─ getter / setter ─ (필요한 만큼 만들어!)
    public Long getReviewpost_id() { return reviewpost_id; }
    public String getReviewposttitle() { return reviewposttitle; }
    public String getReviewpostcontent() { return reviewpostcontent; }
    public int getRating() { return rating; }
    public int getScrapCnt() { return scrapCnt; }
    public Date getCreatedDate() { return createdDate; }
}
