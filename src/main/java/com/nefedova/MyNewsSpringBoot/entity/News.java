package com.nefedova.MyNewsSpringBoot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "news")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

  @Column(name = "published_at")
  private String publishedAt;

  @Column(name = "user_id")
  private Long userId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
  private User author;
}
