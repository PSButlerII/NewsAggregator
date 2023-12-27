package com.recondev.newsaggregator.service;

import com.jzhangdeveloper.newsapi.params.SourcesParams;
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


    public NewsApiService(@Value("${newsapi.apikey}") String apiKey) {
        this.restTemplate = new RestTemplate();
        this.apiKey = apiKey;
    }


    public List<NewsArticle> fetchTopHeadlines(String country/*, String category*/) {

        String url = "https://newsapi.org/v2/top-headlines?country=" + country;
        try {
            // Create HttpHeaders
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            // Use exchange method with the HttpEntity
            ResponseEntity<NewsApiResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<NewsApiResponse>() {
                    }
            );
            NewsApiResponse apiResponse = response.getBody();
            return apiResponse != null ? apiResponse.getArticles() : new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error fetching news: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<NewsArticle> fetchEverything(String query) {
        String url = "https://newsapi.org/v2/everything?q=" + query;

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<NewsApiResponse> response = restTemplate.exchange(
                    url,
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


    public List<String> fetchSources(String language, String country) {
        try {
            String url = "https://newsapi.org/v2/sources?language=" + language + "&country=" + country + "&apiKey=" + apiKey;
            // Call the API and parse the response
            Map<String, String> sourcesParams = SourcesParams.newBuilder()
                    .setCountry("us")
                    .setLanguage("en")
                    .build();
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


    //    public NewsApiService(String yourApiKey) {
//    }
//    NewsAPIClient client = NewsAPI.newClientBuilder()
//            .setApiKey(key)
//            .build();
//
//
//    // an example for "Top Headlines" endpoint
//    Map<String, String> topHeadlineParams = TopHeadlinesParams.newBuilder()
//            .setCountry("us")
//            .setPageSize(10)
//                .build();
//
//    // an example for "Everything" endpoint
//    Map<String, String> everythingParams = EverythingParams.newBuilder()
//            .setPageSize(100)
//            .setSearchQuery("spacex")
//                .build();
//
//    // an example for "SourceRepository" endpoint
//    Map<String, String> sourcesParams = SourcesParams.newBuilder()
//            .setCountry("us")
//            .setLanguage("en")
//        .build();
//
//
//
////    NewAPIResponse response = client.getSources(sourcesParams);
////
////// get status code
////response.getStatusCode()
////
////    // get response body as a Java object and Json object (use Gson)
////    SourceRepository sources = response.getBody();
////    JsonObject sourcesJson = response.getBodyAsJson();
////
////    // get headers
////    Map<String, List<String>>headers = response.getHeaders();
    private List<NewsArticle> mapDtoToNewsArticles(List<NewsArticle> articleDtos) {
        return articleDtos.stream().map(dto -> {
            NewsArticle article = new NewsArticle();
            article.setTitle(dto.getTitle());
            article.setContent(dto.getDescription());
            article.setAuthor(dto.getAuthor());
            article.setPublishedAt(ZonedDateTime.from(dto.getPublishedAt().toLocalDate()));
            // map other fields as needed
            return article;
        }).collect(Collectors.toList());
    }


}
