package com.mpresent.mangoboard.hibernate.dao;

import com.mpresent.mangoboard.hibernate.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {
  Optional<User> findById(String id);
  Integer countById(String id);
  Integer countByPhone(String phone);
}
