package com.nefedova.MyNewsSpringBoot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "news")
@NoArgsConstructor
@AllArgsConstructor
public class News {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "serial")
  private Long newsId;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "url")
  private String url;

  @Column(name = "url_to_image")
  private String urlToImage;

  @Column(name = "published_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private String publishedAt;

  @Column(name = "user_id")
  private Long userId;
}
