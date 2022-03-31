package com.nefedova.MyNewsSpringBoot.dto;

import com.nefedova.MyNewsSpringBoot.entity.News;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsDto {

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
        .author(news.getAuthor() != null ? news.getAuthor().getUsername() : null)
        .build();
  }
}
