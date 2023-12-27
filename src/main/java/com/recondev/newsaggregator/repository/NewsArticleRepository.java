package com.recondev.newsaggregator.repository;

import com.recondev.newsaggregator.model.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
    // custom query methods if needed
}
