package com.present.mango.application.domain.hibernate.dao;

import com.present.mango.application.domain.hibernate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {
  Optional<UserEntity> findById(String id);
  Integer countById(String id);
  Integer countByPhone(String phone);
}
