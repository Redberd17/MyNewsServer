package com.nefedova.MyNewsSpringBoot.service.api;

import com.nefedova.MyNewsSpringBoot.dto.NewsDto;
import com.nefedova.MyNewsSpringBoot.entity.News;
import java.util.List;

public interface NewsService {

  List<News> getAllNews();

  News createNews(NewsDto newsDto, long userId);

  News updateNews(Long id, NewsDto newsDto);

  void deleteNewsById(Long id);

  News findNewsById(Long id);
}
