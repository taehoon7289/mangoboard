package com.present.mango.jooq.command;

import com.present.mango.jooq.generate.tables.TblUserMaster;
import com.present.mango.jooq.generate.tables.pojos.TblUserMasterBean;
import com.present.mango.jooq.generate.tables.records.TblUserMasterRecord;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserCommand {

  DSLContext context;
  TblUserMaster tblUserMaster;

  /**
   * insert user master
   * @param tblUserMasterBean
   * @return
   */
  public Optional<TblUserMasterRecord> insertUserMaster(TblUserMasterBean tblUserMasterBean) {
    TblUserMasterRecord record = context.newRecord(tblUserMaster,tblUserMasterBean);
    record.reset(tblUserMaster.status);
    record.reset(tblUserMaster.regDate);
    record.reset(tblUserMaster.uptDate);
    InsertSetMoreStep<TblUserMasterRecord> insert = context.insertInto(tblUserMaster)
            .set(record);
    return insert.returning()
            .fetchOptional();
  }

}
