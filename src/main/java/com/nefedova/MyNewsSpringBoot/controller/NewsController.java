package com.nefedova.MyNewsSpringBoot.controller;

import com.nefedova.MyNewsSpringBoot.dto.NewsDto;
import com.nefedova.MyNewsSpringBoot.entity.News;
import com.nefedova.MyNewsSpringBoot.entity.User;
import com.nefedova.MyNewsSpringBoot.model.LegacyNewsResponse;
import com.nefedova.MyNewsSpringBoot.service.api.NewsService;
import com.nefedova.MyNewsSpringBoot.service.api.UserService;
import com.nefedova.MyNewsSpringBoot.utils.Constants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/news")
public class NewsController {

  private final NewsService newsService;

  private final UserService userService;

  private final String BASE_URL = "https://newsapi.org/v2/{type}";
  private final String API_KEY = "122c312bd35846c3bc567d40c992cb42";

  @Autowired
  public NewsController(NewsService newsService,
      UserService userService) {
    this.newsService = newsService;
    this.userService = userService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<NewsDto>> getAllNews() {
    List<News> listNews = newsService.getAllNews();
    if (CollectionUtils.isEmpty(listNews)) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(
          listNews.stream()
              .map(NewsDto::newsToDto)
              .collect(Collectors.toList()),
          HttpStatus.OK);
    }
  }

  @GetMapping("/all/paginate")
  public ResponseEntity<List<NewsDto>> getAllNewsPaginated(
      @RequestParam(name = Constants.PAGE_SIZE) Long pageSize,
      @RequestParam(name = Constants.PAGE) Long page) {
    List<News> listNews = newsService.getNewsPaginated(pageSize, page);
    if (CollectionUtils.isEmpty(listNews)) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(
          listNews.stream()
              .map(NewsDto::newsToDto)
              .collect(Collectors.toList()),
          HttpStatus.OK);
    }
  }

  @PostMapping("/new")
  public ResponseEntity<NewsDto> createNews(Authentication authentication,
      @RequestBody NewsDto newNews) {
    String username = authentication.getName();
    User user = userService.getUser(username);
    News news = newsService.createNews(newNews, user.getUserId());
    if (news == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      news.setAuthor(user);
      return new ResponseEntity<>(NewsDto.newsToDto(news), HttpStatus.CREATED);
    }
  }

  @PutMapping("/{newsId}")
  public ResponseEntity<NewsDto> updateNews(Authentication authentication,
      @PathVariable(name = Constants.NEWS_ID) Long newsId,
      @RequestBody NewsDto newsDto) {
    String username = authentication.getName();
    News updatingNews = newsService.findNewsById(newsId);
    if (updatingNews == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No news with provided id");
    }
    if (!username.equals(updatingNews.getAuthor().getUsername())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No rights to change this news");
    }
    News updatedNews = newsService.updateNews(newsId, updatingNews, newsDto);
    if (updatedNews == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "News wasn't updated");
    }
    return new ResponseEntity<>(NewsDto.newsToDto(updatedNews), HttpStatus.OK);
  }

  @DeleteMapping("/{newsId}")
  public ResponseEntity<String> deleteNewsById(Authentication authentication,
      @PathVariable(name = Constants.NEWS_ID) Long newsId) {
    News news = newsService.findNewsById(newsId);
    String username = authentication.getName();
    if (news == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No news with provided id");
    }
    if (!username.equals(news.getAuthor().getUsername())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No rights to delete this news");
    }
    newsService.deleteNewsById(newsId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/top/headlines")
  public ResponseEntity<LegacyNewsResponse> getTopHeadlinesNews(
      @RequestParam(name = Constants.COUNTRY) String country,
      @RequestParam(name = Constants.PAGE) Long page) {
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

  @GetMapping("/everything")
  public ResponseEntity<LegacyNewsResponse> getEverythingNews(
      @RequestParam(name = Constants.Q) String q,
      @RequestParam(name = Constants.PAGE_SIZE) Long pageSize,
      @RequestParam(name = Constants.PAGE) Long page,
      @RequestParam(name = Constants.SORT_BY, required = false) String sortBy) {
    RestTemplate restTemplate = new RestTemplate();
    Map<String, String> urlParams = new HashMap<>();
    urlParams.put(Constants.TYPE, Constants.EVERYTHING);
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL)
        .queryParam(Constants.Q, q)
        .queryParam(Constants.PAGE_SIZE, pageSize)
        .queryParam(Constants.PAGE, page)
        .queryParam(Constants.API_KEY, API_KEY);
    if (Strings.isNotEmpty(sortBy)) {
      builder.queryParam(Constants.SORT_BY, sortBy);
    }
    LegacyNewsResponse newsResponse = restTemplate.getForObject(
        builder.buildAndExpand(urlParams).toUri(), LegacyNewsResponse.class);
    return new ResponseEntity<>(newsResponse, HttpStatus.OK);
  }

}
