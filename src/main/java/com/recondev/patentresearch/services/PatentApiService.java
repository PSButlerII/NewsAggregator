//package com.recondev.patentresearch.services;
//
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//
//import java.io.IOException;
//
//public class PatentApiService {
//
//    public String fetchPatents(String query) throws IOException {
//        String url = "https://example-patent-api.com/search?q=" + query;
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpGet request = new HttpGet(url);
//            // Add headers if required
//            try (CloseableHttpResponse response = httpClient.execute(request)) {
//                return EntityUtils.toString(response.getEntity());
//            }
//        }
//    }
//}
//
