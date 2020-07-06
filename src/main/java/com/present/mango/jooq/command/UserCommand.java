package com.present.mango.jooq.command;

import com.present.mango.common.constant.exception.UserConstException;
import com.present.mango.common.exception.CustomException;
import com.present.mango.jooq.generate.tables.TblUser;
import com.present.mango.jooq.generate.tables.pojos.TblUserBean;
import com.present.mango.jooq.generate.tables.records.TblUserRecord;
import org.jooq.DSLContext;
import org.jooq.InsertOnConflictConditionStep;
import org.jooq.InsertSetMoreStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserCommand {

  DSLContext context;
  TblUser tblUser;

  UserCommand(DSLContext context,
              TblUser tblUser) {
    this.context = context;
    this.tblUser = tblUser;
  }

  /**
   * insert user
   * @param tblUserBean
   * @return
   * @throws CustomException
   */
  public Optional<TblUserRecord> insertUser(TblUserBean tblUserBean) {
    TblUserRecord record = context.newRecord(tblUser,tblUserBean);
    record.reset(tblUser.status);
    record.reset(tblUser.regDate);
    record.reset(tblUser.uptDate);
    InsertSetMoreStep<TblUserRecord> insert = context.insertInto(tblUser)
            .set(record);
    return insert.returning()
            .fetchOptional();
//    record.refresh();
//    record.store();
//    return record;
  }

}
