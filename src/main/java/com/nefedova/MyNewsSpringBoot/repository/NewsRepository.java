package com.nefedova.MyNewsSpringBoot.repository;

import com.nefedova.MyNewsSpringBoot.entity.News;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface NewsRepository extends CrudRepository<News, Long> {

  List<News> findAll();

  void deleteByNewsId(Long newsId);

  News findByNewsId(Long id);

  @Query(value = "SELECT * FROM NEWS LIMIT ?1 OFFSET ?2", nativeQuery = true)
  List<News> getPaginatedNews(Long pageSize, Long startWith);

}
