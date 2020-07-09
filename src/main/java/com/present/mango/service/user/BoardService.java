package com.present.mango.service.user;

import com.present.mango.common.exception.CustomException;
import com.present.mango.form.board.BoardSaveForm;
import com.present.mango.hibernate.dao.BoardDao;
import com.present.mango.hibernate.entity.BoardEntity;
import com.present.mango.jooq.command.user.BoardCommand;
import com.present.mango.jooq.generate.tables.pojos.TblBoardBean;
import com.present.mango.jooq.query.BoardQuery;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Result;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BoardService {

  BoardQuery boardQuery;
  BoardCommand boardCommand;

  BoardService(BoardQuery boardQuery,
               BoardCommand boardCommand) {
    this.boardQuery = boardQuery;
    this.boardCommand = boardCommand;
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
  public List getBoards(Integer page, Integer limit,
                        HttpServletRequest request, HttpServletResponse response) throws CustomException {
    Result result = boardQuery.selectBoards(new HashMap());
    return result.intoMaps();
  }

  /**
   * 게시물 등록/수정
   * @param userNo
   * @param boardSaveForm
   * @return
   */
  public Integer postBoard(Integer userNo, BoardSaveForm boardSaveForm) {
    TblBoardBean tblBoardBean = new TblBoardBean();
    tblBoardBean.setBoardNo(boardSaveForm.getBoardNo());
    tblBoardBean.setTitle(boardSaveForm.getTitle());
    tblBoardBean.setContents(boardSaveForm.getContents());
    tblBoardBean.setUserNo(2);
    tblBoardBean.setImage("dsfdsfasdfsdfsafdd");
    tblBoardBean.setUserNo(userNo);
    Integer boardNo = boardCommand.upsertBoard(tblBoardBean);
    return boardNo;
  }

}
