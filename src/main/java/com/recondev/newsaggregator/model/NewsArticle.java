package com.recondev.newsaggregator.model;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;

/***
 * This class is used to create a NewsArticle object.
 */
@Entity
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "source_id") // Foreign key column in news_article table
    private Source source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private ZonedDateTime publishedAt;
    private String content;

    @ElementCollection
    // This annotation is used to create a one-to-many relationship between NewsArticle and ArticleMetadata
    @CollectionTable(name = "article_metadata", joinColumns = @JoinColumn(name = "article_id"))
    // This annotation is used to create a one-to-many relationship between NewsArticle and ArticleMetadata
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> metadata;


    public NewsArticle() {
    }

    public NewsArticle(Long id, Source source, String author, String title, String description, String url, String urlToImage, ZonedDateTime publishedAt, String content, Map<String, String> metadata) {
        this.id = id;
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
        this.metadata = metadata;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public ZonedDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(ZonedDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsArticle that = (NewsArticle) o;
        return Objects.equals(id, that.id) && Objects.equals(source, that.source) && Objects.equals(author, that.author) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(url, that.url) && Objects.equals(urlToImage, that.urlToImage) && Objects.equals(publishedAt, that.publishedAt) && Objects.equals(content, that.content) && Objects.equals(metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source, author, title, description, url, urlToImage, publishedAt, content, metadata);
    }

    // Implement toString
    @Override
    public String toString() {
        return "NewsArticle{" + "id=" + id + ", title='" + title + ", author=" + author + ", content Preview=" + content + ", publication date= " + publishedAt + "}";
    }

}

