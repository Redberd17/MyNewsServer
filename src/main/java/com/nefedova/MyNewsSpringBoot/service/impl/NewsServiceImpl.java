package com.nefedova.MyNewsSpringBoot.service.impl;

import com.nefedova.MyNewsSpringBoot.dto.NewsDto;
import com.nefedova.MyNewsSpringBoot.entity.News;
import com.nefedova.MyNewsSpringBoot.model.NewsResponse;
import com.nefedova.MyNewsSpringBoot.repository.NewsRepository;
import com.nefedova.MyNewsSpringBoot.service.api.NewsService;
import com.nefedova.MyNewsSpringBoot.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
  public News updateNews(Long id, News updatingNews, NewsDto newsDto) {
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

  @Override
  public NewsResponse getNewsPaginated(Long pageSize, Long page) {
    Long startWith = page == 1 ? 0 : pageSize * (page - 1);
    long totalResults = newsRepository.count();
    List<News> paginatedNews = newsRepository.getPaginatedNews(pageSize, startWith);
    return new NewsResponse(totalResults, paginatedNews.stream()
            .map(NewsDto::newsToDto)
            .collect(Collectors.toList()));
  }
}
