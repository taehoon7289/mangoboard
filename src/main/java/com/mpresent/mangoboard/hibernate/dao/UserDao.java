package com.mpresent.mangoboard.hibernate.dao;

import com.mpresent.mangoboard.hibernate.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {
  User findById(String id);
}
