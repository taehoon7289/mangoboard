package com.present.mango.application.domain.hibernate.dao;

import com.present.mango.application.domain.hibernate.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDao extends JpaRepository<BoardEntity, Integer> {
  Page<BoardEntity> findAll(Pageable pageable);
}
