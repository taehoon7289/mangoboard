package com.present.mango.jooq.command.user;

import com.present.mango.jooq.generate.tables.TblBoard;
import com.present.mango.jooq.generate.tables.pojos.TblBoardBean;
import com.present.mango.jooq.generate.tables.records.TblBoardRecord;
import org.jooq.DSLContext;
import org.jooq.InsertOnDuplicateSetStep;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BoardCommand {
  DSLContext context;
  TblBoard tblBoard;

  BoardCommand(DSLContext context,
               TblBoard tblBoard) {
    this.context = context;
    this.tblBoard = tblBoard;
  }

  /**
   * 게시물 등록/수정
   * @param tblBoardBean
   * @return
   */
  public Integer upsertBoard(TblBoardBean tblBoardBean) {
    TblBoardRecord record = context.newRecord(tblBoard, tblBoardBean);
    if (record.getBoardNo() < 1) {
      record.reset(tblBoard.boardNo);
    }
    // Database default value reset
    record.reset(tblBoard.status);
    record.reset(tblBoard.regDate);
    record.reset(tblBoard.uptDate);
    InsertOnDuplicateSetStep<TblBoardRecord> insert = context.insertInto(tblBoard)
            .set(record)
            .onDuplicateKeyUpdate();
    // 수정일 갱신
    record.setUptDate(LocalDateTime.now());
    return insert.set(record)
            .returning()
            .fetchOne()
            .getBoardNo();
  }
}
