package com.present.mango.application.service;

import com.present.mango.application.form.board.BoardSaveForm;
import com.present.mango.common.constant.exception.BoardConstException;
import com.present.mango.common.exception.CustomException;
import com.present.mango.application.domain.jooq.command.BoardCommand;
import com.present.mango.application.domain.jooq.generate.tables.pojos.TblBoardMasterBean;
import com.present.mango.application.domain.jooq.generate.tables.pojos.TblUserMasterBean;
import com.present.mango.application.domain.jooq.generate.tables.records.TblBoardMasterRecord;
import com.present.mango.application.domain.jooq.generate.tables.records.TblUserMasterRecord;
import com.present.mango.application.domain.jooq.query.BoardQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class BoardService {

  BoardQuery boardQuery;
  BoardCommand boardCommand;

  /**
   * 게시글 리스트 (페이징 처리)
   * @param pageable
   * @param searchText
   * @return
   * @throws CustomException
   */
  @Transactional
  public Page getBoardList(Pageable pageable, String searchText) {
    Map params = new HashMap();
    params.put("searchText",searchText);
    Result<Record> result = boardQuery.selectPageBoardAndUser(params,pageable);
    List<Map> resultList = result.stream().map(record -> {
      Map resultMap = new HashMap();
      resultMap.put("board", record.into(TblBoardMasterRecord.class).into(TblBoardMasterBean.class));
      resultMap.put("writer", record.into(TblUserMasterRecord.class).into(TblUserMasterBean.class));
      return resultMap;
    }).collect(Collectors.toList());
    return new PageImpl(resultList,pageable,boardQuery.selectPageBoardAndUserCnt(params));
  }

  /**
   * 게시물 등록/수정
   * @param userNo
   * @param boardSaveForm
   * @return
   */
  @Transactional
  public Integer saveBoard(Integer userNo, BoardSaveForm boardSaveForm) throws CustomException {
    // 수정권한 체크
    if (boardSaveForm.getBoardNo() > 0) {
      Map params = new HashMap();
      params.put("userNo",userNo);
      params.put("boardNo",boardSaveForm.getBoardNo());
      boardQuery.selectRecordBoard(params)
              .orElseThrow(() -> new CustomException(BoardConstException.INVALID_BOARD_NO));
    }
    TblBoardMasterBean tblBoardMasterBean = new TblBoardMasterBean();
    tblBoardMasterBean.setBoardNo(boardSaveForm.getBoardNo());
    tblBoardMasterBean.setTitle(boardSaveForm.getTitle());
    tblBoardMasterBean.setContents(boardSaveForm.getContents());
    tblBoardMasterBean.setUserNo(userNo);
    Integer boardNo = boardCommand.upsertBoardMaster(tblBoardMasterBean);
    return boardNo;
  }

}
