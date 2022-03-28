package com.nefedova.MyNewsSpringBoot.service;

import com.nefedova.MyNewsSpringBoot.entity.News;
import java.util.List;

public interface NewsService {

  List<News> getAllNews();

  News createNews(News newNews);

  News updateNews(Long id, News news);

  void deleteNewsById(Long id);

  News findNewsById(Long id);
}
