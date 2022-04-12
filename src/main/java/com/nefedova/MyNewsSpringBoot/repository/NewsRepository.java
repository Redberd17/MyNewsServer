package com.nefedova.MyNewsSpringBoot.repository;

import com.nefedova.MyNewsSpringBoot.entity.News;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface NewsRepository extends CrudRepository<News, Long> {

  List<News> findAll();

  void deleteByNewsId(Long newsId);

  @Query(value = "SELECT * FROM NEWS ORDER BY published_at LIMIT ?1 OFFSET ?2", nativeQuery = true)
  List<News> getPaginatedNews(Long pageSize, Long startWith);

  long count();
}
