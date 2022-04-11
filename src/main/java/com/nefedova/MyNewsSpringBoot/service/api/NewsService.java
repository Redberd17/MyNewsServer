package com.nefedova.MyNewsSpringBoot.service.api;

import com.nefedova.MyNewsSpringBoot.dto.NewsDto;
import com.nefedova.MyNewsSpringBoot.entity.News;
import com.nefedova.MyNewsSpringBoot.model.NewsResponse;

import java.util.List;

public interface NewsService {

  List<News> getAllNews();

  News createNews(NewsDto newsDto, long userId);

  News updateNews(Long id, News updatingNews, NewsDto newsDto);

  void deleteNewsById(Long id);

  News findNewsById(Long id);

  NewsResponse getNewsPaginated(Long pageSize, Long page);
}
