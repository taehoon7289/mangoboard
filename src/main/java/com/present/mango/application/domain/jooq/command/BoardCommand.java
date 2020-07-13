package com.present.mango.application.domain.jooq.command;

import com.present.mango.application.domain.jooq.generate.tables.TblBoardMaster;
import com.present.mango.application.domain.jooq.generate.tables.pojos.TblBoardMasterBean;
import com.present.mango.application.domain.jooq.generate.tables.records.TblBoardMasterRecord;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.InsertOnDuplicateSetStep;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@AllArgsConstructor
@Repository
public class BoardCommand {
  DSLContext context;
  TblBoardMaster tblBoardMaster;

  /**
   * 게시물 등록/수정
   * @param tblBoardMasterBean
   * @return
   */
  public Integer upsertBoardMaster(TblBoardMasterBean tblBoardMasterBean) {
    TblBoardMasterRecord record = context.newRecord(tblBoardMaster, tblBoardMasterBean);
    if (record.getBoardNo() < 1) {
      record.reset(tblBoardMaster.boardNo);
    }
    // Database default value reset
    record.reset(tblBoardMaster.status);
    record.reset(tblBoardMaster.regDate);
    record.reset(tblBoardMaster.uptDate);
    InsertOnDuplicateSetStep<TblBoardMasterRecord> insert = context.insertInto(tblBoardMaster)
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
