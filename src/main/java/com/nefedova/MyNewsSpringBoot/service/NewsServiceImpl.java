package com.nefedova.MyNewsSpringBoot.service;

import com.nefedova.MyNewsSpringBoot.entity.News;
import com.nefedova.MyNewsSpringBoot.repository.NewsRepository;
import java.util.List;
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
    return newsRepository.save(news);
  }

  @Override
  public News updateNews(News news) {
    return newsRepository.save(news);
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
