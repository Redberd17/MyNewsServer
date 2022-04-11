package com.nefedova.MyNewsSpringBoot.dto;

import com.nefedova.MyNewsSpringBoot.entity.News;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsDto {

  @Nullable
  private Long id;

  private String title;

  private String description;

  private String url;

  private String urlToImage;

  private String author;

  private String publishedAt;

  public static NewsDto newsToDto(News news) {
    return NewsDto.builder()
        .id(news.getNewsId())
        .title(news.getTitle())
        .description(news.getDescription())
        .url(news.getUrl())
        .urlToImage(news.getUrlToImage())
        .publishedAt(news.getPublishedAt())
        .author(news.getAuthor().getUsername())
        .build();
  }

  public static News dtoToNews(NewsDto newsDto) {
    return News.builder()
        .title(newsDto.getTitle())
        .description(newsDto.getDescription())
        .url(newsDto.getUrl())
        .urlToImage(newsDto.getUrlToImage())
        .build();
  }
}
