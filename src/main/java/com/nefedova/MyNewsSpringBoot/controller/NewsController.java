package com.nefedova.MyNewsSpringBoot.controller;

import com.nefedova.MyNewsSpringBoot.entity.News;
import com.nefedova.MyNewsSpringBoot.model.LegacyNewsResponse;
import com.nefedova.MyNewsSpringBoot.service.NewsService;
import com.nefedova.MyNewsSpringBoot.utils.Constants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/news")
public class NewsController {

  private final NewsService newsService;

  private final String BASE_URL = "https://newsapi.org/v2/{type}";
  private final String API_KEY = "122c312bd35846c3bc567d40c992cb42";

  @Autowired
  public NewsController(NewsService newsService) {
    this.newsService = newsService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<News>> getAllNews() {
    List<News> listNews = newsService.getAllNews();
    if (CollectionUtils.isEmpty(listNews)) {
      return new ResponseEntity<>(listNews, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(listNews, HttpStatus.NO_CONTENT);
    }
  }

  @PostMapping("/new")
  public ResponseEntity<News> createNews(@RequestBody News newNews) {
    News news = newsService.createNews(newNews);
    if (news == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<>(news, HttpStatus.CREATED);
    }
  }

  @PutMapping("/{newsId}")
  public ResponseEntity<News> updateNews(@PathVariable(name = Constants.NEWS_ID) Long newsId,
      @RequestBody News news) {
    News updatingNews = newsService.updateNews(newsId, news);
    if (updatingNews == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(updatingNews, HttpStatus.OK);
  }

  @DeleteMapping("/{newsId}")
  public ResponseEntity<String> deleteNewsById(
      @PathVariable(name = Constants.NEWS_ID) Long newsId) {
    News news = newsService.findNewsById(newsId);
    if (news == null) {
      return new ResponseEntity<>("No news with provided id", HttpStatus.NOT_FOUND);
    }
    newsService.deleteNewsById(newsId);
    return new ResponseEntity<>("News was successfully removed", HttpStatus.OK);
  }

  @GetMapping("/top/headlines/{country}/{page}")
  public ResponseEntity<LegacyNewsResponse> getTopHeadlinesNews(
      @PathVariable(name = Constants.COUNTRY) String country,
      @PathVariable(name = Constants.PAGE) Long page) {
    RestTemplate restTemplate = new RestTemplate();
    Map<String, String> urlParams = new HashMap<>();
    urlParams.put(Constants.TYPE, Constants.TOP_HEADLINES);
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL)
        .queryParam(Constants.COUNTRY, country)
        .queryParam(Constants.PAGE, page)
        .queryParam(Constants.API_KEY, API_KEY);
    LegacyNewsResponse newsResponse = restTemplate.getForObject(
        builder.buildAndExpand(urlParams).toUri(), LegacyNewsResponse.class);
    return new ResponseEntity<>(newsResponse, HttpStatus.OK);
  }

}
