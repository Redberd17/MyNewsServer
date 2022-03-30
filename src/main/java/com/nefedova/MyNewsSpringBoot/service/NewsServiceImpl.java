package com.nefedova.MyNewsSpringBoot.service;

import com.nefedova.MyNewsSpringBoot.entity.News;
import com.nefedova.MyNewsSpringBoot.repository.NewsRepository;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {

  private final NewsRepository newsRepository;

  @Autowired
  public NewsServiceImpl(NewsRepository newsRepository) {
    this.newsRepository = newsRepository;
  }

  @Override
  public List<News> getAllNews() {
    return newsRepository.findAll();
  }

  @Override
  public News createNews(News news) {
    TimeZone tz = TimeZone.getTimeZone("UTC");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    df.setTimeZone(tz);
    String currentDate = df.format(new Date());
    news.setPublishedAt(currentDate);
    return newsRepository.save(news);
  }

  @Override
  public News updateNews(Long id, News news) {
    News updatingNews = newsRepository.findByNewsId(id);
    if (updatingNews == null) {
      return null;
    }
    updatingNews.setTitle(news.getTitle());
    updatingNews.setDescription(news.getDescription());
    updatingNews.setUrl(news.getUrl());
    updatingNews.setUrlToImage(news.getUrlToImage());
    updatingNews.setPublishedAt(news.getPublishedAt());
    return newsRepository.save(updatingNews);
  }

  @Override
  public void deleteNewsById(Long id) {
    newsRepository.deleteByNewsId(id);
  }

  @Override
  public News findNewsById(Long id) {
    return newsRepository.findById(id).orElse(null);
  }
}
