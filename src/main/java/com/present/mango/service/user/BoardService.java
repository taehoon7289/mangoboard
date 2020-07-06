package com.present.mango.service.user;

import com.present.mango.common.exception.CustomException;
import com.present.mango.hibernate.dao.BoardDao;
import com.present.mango.hibernate.entity.BoardEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class BoardService {

  BoardDao boardDao;
  PasswordEncoder passwordEncoder;

  BoardService(BoardDao boardDao,
               PasswordEncoder passwordEncoder) {
    this.boardDao = boardDao;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * 게시글 리스트 (페이징 처리)
   * @param page
   * @param limit
   * @param request
   * @param response
   * @return
   * @throws CustomException
   */
  public Page getBoards(Integer page, Integer limit,
                            HttpServletRequest request, HttpServletResponse response) throws CustomException {
    Pageable pageable = PageRequest.of(page,limit, Sort.by(Sort.Direction.DESC,"boardNo"));
    Page<BoardEntity> result = boardDao.findAll(pageable);
    log.info("pageResultpageResult :: {}", result);
    return result;
  }

  public Integer setBoard(String title,String contents) {
    BoardEntity boardEntity = new BoardEntity();
    boardEntity.setTitle(title);
    boardEntity.setContents(contents);
    boardEntity.setUserNo(2);
    boardEntity.setImage("dsfdsfasdfsdfsafdd");
    boardDao.save(boardEntity);
    return boardEntity.getBoardNo();
  }

}
