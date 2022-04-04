package com.nefedova.MyNewsSpringBoot.service.impl;

import com.nefedova.MyNewsSpringBoot.dto.NewsDto;
import com.nefedova.MyNewsSpringBoot.entity.News;
import com.nefedova.MyNewsSpringBoot.repository.NewsRepository;
import com.nefedova.MyNewsSpringBoot.service.api.NewsService;
import com.nefedova.MyNewsSpringBoot.utils.DateUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional
  public News createNews(NewsDto newsDto, long userId) {
    News news = News.builder()
        .title(newsDto.getTitle())
        .description(newsDto.getDescription())
        .url(newsDto.getUrl())
        .urlToImage(newsDto.getUrlToImage())
        .publishedAt(DateUtil.getCurrentDateUTC())
        .userId(userId)
        .build();
    return newsRepository.save(news);
  }

  @Override
  public News updateNews(Long id, NewsDto newsDto) {
    News updatingNews = newsRepository.findByNewsId(id);
    if (updatingNews == null) {
      return null;
    }
    updatingNews.setTitle(newsDto.getTitle());
    updatingNews.setDescription(newsDto.getDescription());
    updatingNews.setUrl(newsDto.getUrl());
    updatingNews.setUrlToImage(newsDto.getUrlToImage());
    updatingNews.setPublishedAt(DateUtil.getCurrentDateUTC());
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
