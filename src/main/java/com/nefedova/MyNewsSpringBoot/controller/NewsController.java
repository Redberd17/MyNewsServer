package com.nefedova.MyNewsSpringBoot.controller;

import com.nefedova.MyNewsSpringBoot.entity.News;
import com.nefedova.MyNewsSpringBoot.service.NewsService;
import java.rmi.ServerException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsController {

  private final NewsService newsService;

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
  public ResponseEntity<News> updateNews(@PathVariable(name = "newsId") Long newsId,
      @RequestBody News news) {
    News updatingNews = newsService.findNewsById(newsId);
    if (updatingNews != null) {
      updatingNews.setTitle(news.getTitle());
      updatingNews.setDescription(news.getDescription());
      updatingNews.setUrl(news.getUrl());
      updatingNews.setUrlToImage(news.getUrlToImage());
      updatingNews.setPublishedAt(news.getPublishedAt());
      News updatedNews = newsService.updateNews(updatingNews);
      return new ResponseEntity<>(updatedNews, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{newsId}")
  public ResponseEntity<News> deleteNewsById(@PathVariable(name = "newsId") Long newsId) {
    newsService.deleteNewsById(newsId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
