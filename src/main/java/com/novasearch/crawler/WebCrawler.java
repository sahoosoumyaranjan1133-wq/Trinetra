package com.novasearch.crawler;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.novasearch.service.SearchService;

@Service
public class WebCrawler {

    private static final int MAX_PAGES = 10;
    private static final int DELAY_MS = 1500;

    private final SearchService searchService;

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public WebCrawler(SearchService searchService) {
        this.searchService = searchService;
    }

    public void crawl(String startUrl) throws Exception {

        URI startUri = URI.create(startUrl);

        if (!"http".equalsIgnoreCase(startUri.getScheme())
                && !"https".equalsIgnoreCase(startUri.getScheme())) {
            throw new IllegalArgumentException("Only HTTP and HTTPS URLs are allowed.");
        }

        String allowedHost = startUri.getHost();

        if (allowedHost == null) {
            throw new IllegalArgumentException("Invalid starting URL.");
        }

        ArrayDeque<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        queue.add(startUri.toString());

        while (!queue.isEmpty() && visited.size() < MAX_PAGES) {

            String url = queue.poll();

            URI pageUri = URI.create(url);

            if (!allowedHost.equalsIgnoreCase(pageUri.getHost())) {
                continue;
            }

            if (!visited.add(url)) {
                continue;
            }

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(pageUri)
                        .timeout(Duration.ofSeconds(15))
                        .header("User-Agent", "TrinetraBot/1.0")
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    continue;
                }

                String contentType = response.headers()
                        .firstValue("Content-Type")
                        .orElse("");

                if (!contentType.toLowerCase().contains("text/html")) {
                    continue;
                }

                Document document = Jsoup.parse(response.body(), url);

                String title = document.title();
                String content = document.body().text();

                searchService.indexDocument(title, url, content);

                System.out.println("Indexed: " + url);

                for (Element link : document.select("a[href]")) {

                    String nextUrl = link.absUrl("href");

                    if (nextUrl.isBlank()) {
                        continue;
                    }

                    URI nextUri = URI.create(nextUrl);

                    if (allowedHost.equalsIgnoreCase(nextUri.getHost())
                            && !visited.contains(nextUrl)) {
                        queue.add(nextUrl);
                    }
                }

                Thread.sleep(DELAY_MS);

            } catch (IOException e) {
                System.err.println("Unable to crawl " + url + ": " + e.getMessage());
            }
        }

        System.out.println("Crawling completed. Visited: " + visited.size());
    }
}
