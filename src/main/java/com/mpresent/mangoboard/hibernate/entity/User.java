package com.mpresent.mangoboard.hibernate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tblUser",schema = "mangoBoard",catalog = "mangoBoard")
@DynamicInsert
@DynamicUpdate
@Getter @Setter
public class User {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column Integer userNo;
  @Column String id;
  @Column String password;
  @Column String name;
  @Column String gender;
  @Column String phone;
  @Column(columnDefinition = "TINYINT(1) DEFAULT 1") Integer status;

  @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime regDate;
  @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime uptDate;
  @Column @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime lastLoginDate;

  @OneToMany @JoinColumn(name = "userNo", referencedColumnName = "userNo")
  Collection<Board> boards;
}
