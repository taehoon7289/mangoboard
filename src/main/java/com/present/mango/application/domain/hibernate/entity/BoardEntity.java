package com.present.mango.application.domain.hibernate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tblBoard",schema = "mangoBoard",catalog = "mangoBoard")
@DynamicInsert
@DynamicUpdate
@Getter @Setter
public class BoardEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column Integer boardNo;
  @Column(columnDefinition = "VARCHAR(512)") String title;
  @Column(columnDefinition = "MEDIUMTEXT") String contents;
  @Column(columnDefinition = "VARCHAR(512)") String image;
  @Column(columnDefinition = "TINYINT(1) DEFAULT 1") Integer status;
  @Column Integer userNo;
  @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime regDate;
  @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime uptDate;

  @ManyToOne(optional = false) @JoinColumn(name = "userNo", referencedColumnName = "userNo",insertable=false, updatable=false)
  UserEntity userEntity;

}
