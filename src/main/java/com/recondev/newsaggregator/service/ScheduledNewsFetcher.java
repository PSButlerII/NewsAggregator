package com.recondev.newsaggregator.service;

import com.recondev.newsaggregator.enums.Enums;
import com.recondev.newsaggregator.model.NewsArticle;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class ScheduledNewsFetcher {

    private final NewsFetchingService newsFetchingService;

    public ScheduledNewsFetcher(NewsFetchingService newsFetchingService) {
        this.newsFetchingService = newsFetchingService;
    }

    @Scheduled(fixedRate = 100000) // Fetch news every 100 seconds (as an example)
    public void fetchNewsRegularly() {
        // Specify the news fetching strategy (e.g., API, RSS, WEB_SCRAPING)
        List<NewsArticle> articles = newsFetchingService.fetchLatestNews(Enums.NewsFetchingStrategy.API);

        // Save articles to the database or perform other operations
        // Example: saveArticles(articles);
    }

    // Additional methods as needed, such as saveArticles
}
