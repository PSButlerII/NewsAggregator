package com.recondev.newsaggregator.service;

import com.recondev.newsaggregator.dto.NewsApiResponse;
import com.recondev.newsaggregator.model.NewsArticle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NewsApiService {
    private final String apiKey;
    private final RestTemplate restTemplate;

    private final String newsUrl = "https://newsapi.org/v2/";


    public NewsApiService(@Value("${newsapi.apikey}") String apiKey) {
        this.restTemplate = new RestTemplate();
        this.apiKey = apiKey;
    }

    public List<NewsArticle> fetchTopHeadlines(String country, Integer pageSize, Integer page) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(newsUrl + "top-headlines")
                .queryParam("country", country)
                .queryParam("apiKey", apiKey);

        // Add pagination parameters if not null
        if (pageSize > 0) builder.queryParam("pageSize", pageSize);
        if (page > 0) builder.queryParam("page", page);


        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<NewsApiResponse> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<NewsApiResponse>() {
                }
        );

        NewsApiResponse apiResponse = response.getBody();
        return apiResponse != null ? mapDtoToNewsArticles(apiResponse.getArticles()) : new ArrayList<>();
    }


    public List<String> fetchSources(String language, String country) {
        try {

            String url = newsUrl + "sources?language=" + language + "&country=" + country + "&apiKey=" + apiKey;
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            // Call the API and parse the response using the ParameterizedTypeReference
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    }

            );
            Map<String, Object> apiResponse = response.getBody();
            List<String> sources = apiResponse != null ? (List<String>) apiResponse.get("sources") : new ArrayList<>();
            return sources;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(); // Return list of source names or identifiers
    }

    private NewsArticle mapToNewsArticle(Map<String, Object> articleData) {
        NewsArticle article = new NewsArticle();
        article.setTitle((String) articleData.get("title"));
        article.setContent((String) articleData.get("description")); // Adjust the field names based on actual response
        article.setPublishedAt(ZonedDateTime.from((LocalDate) articleData.get("")));

        // ... set other properties
        return article;
    }

    private List<NewsArticle> mapDtoToNewsArticles(List<NewsArticle> articleDtos) {
        return articleDtos.stream().map(dto -> {
            NewsArticle article = new NewsArticle();
            article.setTitle(dto.getTitle());
            article.setContent(dto.getDescription());
            article.setAuthor(dto.getAuthor());
            article.setPublishedAt(dto.getPublishedAt());


            // map other fields as needed
            return article;
        }).collect(Collectors.toList());
    }

    public List<NewsArticle> fetchEverything(String query, String searchIn, String domains, Object excludeDomains,
                                             LocalDate from, LocalDate to, String language, String sortBy,
                                             Integer pageSize, Integer page) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            if (encodedQuery.length() > 500) {
                throw new IllegalArgumentException("Query is too long");
            }

            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl(newsUrl + "everything")
                    .queryParam("q", encodedQuery);

            // Add optional parameters if they are not null
            if (searchIn != null) builder.queryParam("searchIn", searchIn);
            if (domains != null) builder.queryParam("domains", domains);
            if (excludeDomains != null) builder.queryParam("excludeDomains", excludeDomains);
            if (from != null) builder.queryParam("from", from.toString());
            if (to != null) builder.queryParam("to", to.toString());
            if (language != null) builder.queryParam("language", language);
            if (sortBy != null) builder.queryParam("sortBy", sortBy);
            if (pageSize != null) builder.queryParam("pageSize", pageSize);
            if (page != null) builder.queryParam("page", page);

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<NewsApiResponse> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<NewsApiResponse>() {
                    }
            );

            NewsApiResponse apiResponse = response.getBody();

            return apiResponse != null ? mapDtoToNewsArticles(apiResponse.getArticles()) : new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error fetching news: " + e.getMessage());
            return new ArrayList<>();
        }
    }


}
