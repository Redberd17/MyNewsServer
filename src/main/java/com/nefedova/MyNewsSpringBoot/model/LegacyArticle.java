package com.nefedova.MyNewsSpringBoot.model;

import lombok.Data;

@Data
public class LegacyArticle {

  String title;
  String description;
  String url;
  String urlToImage;
  String publishedAt;
}
