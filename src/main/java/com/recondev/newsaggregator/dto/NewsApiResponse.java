package com.recondev.newsaggregator.dto;

import com.recondev.newsaggregator.model.NewsArticle;

import java.util.List;

/***
 * This class is used to map the response from the News API.
 */
public class NewsApiResponse {
    private String status;
    private int totalResults;
    //private List<NewsArticle> articles;
    private List<NewsArticle> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<NewsArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsArticle> articles) {
        this.articles = articles;
    }
}
