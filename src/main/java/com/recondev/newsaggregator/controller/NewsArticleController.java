package com.recondev.newsaggregator.controller;

import com.recondev.newsaggregator.enums.Enums;
import com.recondev.newsaggregator.model.NewsArticle;
import com.recondev.newsaggregator.service.NewsDatabaseService;
import com.recondev.newsaggregator.service.NewsFetchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.recondev.newsaggregator.enums.Enums.NewsFetchingStrategy.API;

/**
 * This class is the controller for the NewsArticle model.
 */
@RestController
@RequestMapping("/api/news")
public class NewsArticleController {

    private final NewsDatabaseService newsDatabaseService;
    private final NewsFetchingService newsFetchingService;

    @Autowired
    public NewsArticleController(NewsDatabaseService newsDatabaseService, NewsFetchingService newsFetchingService) {
        this.newsDatabaseService = newsDatabaseService;
        this.newsFetchingService = newsFetchingService;
    }

    // Get all news articles
    @GetMapping
    public ResponseEntity<List<NewsArticle>> getAllNews() {
        List<NewsArticle> articles = newsDatabaseService.getAllNews();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/external")
    public ResponseEntity<List<NewsArticle>> getAllNewsFromApi() {
        // ... implementation ...
        List<NewsArticle> articles = newsFetchingService.fetchLatestNews(API);
        return ResponseEntity.ok(articles);
    }

    // Get a specific news article by ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<NewsArticle>> getNewsArticleById(@PathVariable Long id) {
        Optional<NewsArticle> article = newsDatabaseService.getNewsArticleById(id);
        if (article != null) {
            return ResponseEntity.ok(article);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new news article
    @PostMapping("/addArticle")
    public ResponseEntity<NewsArticle> createNewsArticle(@RequestBody NewsArticle newsArticle) {
        NewsArticle savedArticle = newsDatabaseService.saveNewsArticle(newsArticle);
        return ResponseEntity.ok(savedArticle);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<NewsArticle>> getLatestNews(@RequestParam Enums.NewsFetchingStrategy strategy) {
        try {
            List<NewsArticle> articles = newsFetchingService.fetchLatestNews(strategy);
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Update an existing news article
    @PutMapping("/{id}")
    public ResponseEntity<NewsArticle> updateNewsArticle(@PathVariable Long id, @RequestBody NewsArticle newsArticle) {
        NewsArticle updatedArticle = newsDatabaseService.updateNewsArticle(id, newsArticle);
        if (updatedArticle != null) {
            return ResponseEntity.ok(updatedArticle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a news article
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNewsArticle(@PathVariable Long id) {
        boolean deleted = newsDatabaseService.deleteNewsArticle(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/showNews")
    public String showNews(Model model) {
        model.addAttribute("articles", newsFetchingService.fetchLatestNews(API));
        return "news";
    }
    // Additional endpoints as needed
}

