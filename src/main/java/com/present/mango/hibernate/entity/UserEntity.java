package com.present.mango.hibernate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "tblUser",schema = "mangoBoard",catalog = "mangoBoard")
@DynamicInsert
@DynamicUpdate
@Getter @Setter
public class UserEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column Integer userNo;
  @Column(columnDefinition = "VARCHAR(128)") String id;
  @Column(columnDefinition = "VARCHAR(256)") String password;
  @Column(columnDefinition = "VARCHAR(128)") String name;
  @Column(columnDefinition = "VARCHAR(1)") String gender;
  @Column(columnDefinition = "VARCHAR(32)") String phone;
  @Column(columnDefinition = "TINYINT(1) DEFAULT 1") Integer status;

  @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime regDate;
  @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime uptDate;
  @Column @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime lastLoginDate;

  @Column(columnDefinition = "VARCHAR(256)") String refreshToken;

  @OneToMany @JoinColumn(name = "userNo", referencedColumnName = "userNo")
  Collection<BoardEntity> boardEntities;
}
