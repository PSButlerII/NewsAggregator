package com.recondev.newsaggregator.service;

import com.recondev.newsaggregator.model.NewsArticle;
import com.recondev.newsaggregator.model.Source;
import com.recondev.newsaggregator.repository.NewsArticleRepository;
import com.recondev.newsaggregator.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsDatabaseService {
    @Autowired
    private NewsArticleRepository repository;
    @Autowired
    private SourceRepository sourceRepository; // Assuming you have a repository for Source


    public NewsArticle saveNewsArticle(NewsArticle newsArticle) {
        Source source = newsArticle.getSource();
        if (source != null) {
            if (source.getId() == null || !sourceRepository.existsById(source.getId())) {
                // Set the Source ID if not present
                source.setId(source.getName());
                source = sourceRepository.save(source);
            }
            newsArticle.setSource(source);
        }
        return repository.save(newsArticle);
    }

    public List<NewsArticle> getAllNews() {
        return repository.findAll();
    }

    public Optional<NewsArticle> getNewsArticleById(Long id) {
        return repository.findById(id);
    }

    public NewsArticle updateNewsArticle(Long id, NewsArticle newsArticle) {
        return newsArticle;
    }

    public boolean deleteNewsArticle(Long id) {
        return false;
    }
    // Additional methods for update, delete, etc.
}

