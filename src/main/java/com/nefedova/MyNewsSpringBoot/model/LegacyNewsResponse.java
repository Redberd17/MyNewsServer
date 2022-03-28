package com.nefedova.MyNewsSpringBoot.model;

import java.util.List;
import lombok.Data;

@Data
public class LegacyNewsResponse {

  Integer totalResults;
  List<LegacyArticle> articles;
}
