package com.mpresent.mangoboard.hibernate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tblUser")
@Getter @Setter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="userNo")
  Integer userNo;
  @Column(name="id") String id;
  @Column(name="password") String password;
}
