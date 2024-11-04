package test.entity;

import java.util.Date;

public class AnnouncementDto {
    private Long id;
    private String title;
    private String content;
    private Date date;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public String getFirstLineOfContent() {
        if (content != null && content.contains("\n")) {
            return content.substring(0, content.indexOf('\n'));
        }
        return content;
    }
}
