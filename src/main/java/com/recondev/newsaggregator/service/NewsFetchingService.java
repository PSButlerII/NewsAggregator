package com.recondev.newsaggregator.service;

import com.recondev.newsaggregator.dto.NewsQueryDto;
import com.recondev.newsaggregator.enums.Enums;
import com.recondev.newsaggregator.model.NewsArticle;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsFetchingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsFetchingService.class);

    private final NewsApiService newsApiService;

    @Value("${news.rss.feed.url}")
    private String rssFeedUrl;

    @Value("${news.web.scrape.url}")
    private String webScrapeUrl;

    public NewsFetchingService(NewsApiService newsApiService) {
        this.newsApiService = newsApiService;
    }

    public List<NewsArticle> fetchTheNews(Enums.NewsFetchingStrategy strategy, NewsQueryDto params) {
        switch (strategy) {
            case API:
                return fetchFromApi(params);
            case RSS:
                return fetchFromRss();
            case WEB_SCRAPING:
                return fetchByWebScraping();
            default:
                return new ArrayList<>();
        }
    }

    private List<NewsArticle> fetchFromApi(NewsQueryDto params) {
        if (params == null) {
            return newsApiService.fetchTopHeadlines("us", 100, 1);
        } else {
            return newsApiService.fetchEverything(params.getQuery(), params.getSearchIn(),
                    params.getDomains(), params.getExcludeDomains(),
                    params.getFrom(), params.getTo(),
                    params.getLanguage(), params.getSortBy(),
                    params.getPageSize(), params.getPage());
//            case 3:
//                return newsApiService.fetchSources(null,null);
        }

    }

    private List<NewsArticle> fetchFromRss() {
        List<NewsArticle> articles = new ArrayList<>();
        try {
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(rssFeedUrl)));
            for (SyndEntry entry : feed.getEntries()) {
                NewsArticle article = mapSyndEntryToNewsArticle(entry);
                articles.add(article);
            }
        } catch (IOException | FeedException e) {
            LOGGER.error("Error fetching RSS feed: {}", e.getMessage(), e);
        }
        return articles;
    }

    private NewsArticle mapSyndEntryToNewsArticle(SyndEntry entry) {
        NewsArticle article = new NewsArticle();
        article.setTitle(entry.getTitle());
        article.setContent(entry.getDescription().getValue());
        article.setAuthor(entry.getAuthor());

        // Convert to ZonedDateTime assuming start of the day in system default timezone
        LocalDate localDate = entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        article.setPublishedAt(zonedDateTime);

        return article;
    }


    private List<NewsArticle> fetchByWebScraping() {
        List<NewsArticle> articles = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(webScrapeUrl).get();
            Elements newsElements = doc.select("css-query-for-news-item");
            for (Element element : newsElements) {
                // Implement web scraping logic to populate NewsArticle objects
            }
        } catch (IOException e) {
            LOGGER.error("Error during web scraping: {}", e.getMessage(), e);
        }
        return articles;
    }

    public List<NewsArticle> fetchFromApiWithParameters(NewsQueryDto newsQueryDto) {
        return newsApiService.fetchEverything(newsQueryDto.getQuery(), newsQueryDto.getSearchIn(),
                newsQueryDto.getDomains(), newsQueryDto.getExcludeDomains(), newsQueryDto.getFrom(),
                newsQueryDto.getTo(), newsQueryDto.getLanguage(), newsQueryDto.getSortBy(), newsQueryDto.getPageSize(), newsQueryDto.getPage());

    }
}
