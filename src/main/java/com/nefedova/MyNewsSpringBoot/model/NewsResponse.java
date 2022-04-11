package com.nefedova.MyNewsSpringBoot.model;

import com.nefedova.MyNewsSpringBoot.dto.NewsDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewsResponse {

  long totalResults;
  List<NewsDto> articles;
}
