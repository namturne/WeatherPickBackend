package WeatherPick.weatherpick.domain.review.dto;

import java.util.Date;

public class ReviewCommentDto {
    private Long id;
    private String username;
    private String content;
    private Date createdDate;

    public ReviewCommentDto() {}

    public ReviewCommentDto(Long id, String username, String content, Date createdDate) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.createdDate = createdDate;
    }

    // ─── getters/setters ───
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
}
