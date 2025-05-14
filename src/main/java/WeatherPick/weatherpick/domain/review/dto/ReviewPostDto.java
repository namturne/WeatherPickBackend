package WeatherPick.weatherpick.domain.review.dto;

import java.util.Date;

public class ReviewPostDto {
    private Long id;
    private String title;
    private String content;
    private int rating;
    private int scrapCount;
    private Date createdDate;

    public ReviewPostDto() {}

    public ReviewPostDto(Long id, String title, String content, int rating, int scrapCount, Date createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.scrapCount = scrapCount;
        this.createdDate = createdDate;
    }

    // ─── getters/setters ───
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public int getScrapCount() { return scrapCount; }
    public void setScrapCount(int scrapCount) { this.scrapCount = scrapCount; }

    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
}
