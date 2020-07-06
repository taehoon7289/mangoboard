package com.present.mango.jooq.query;

import com.present.mango.jooq.generate.tables.TblUser;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class UserQuery {

  DSLContext context;
  TblUser tblUser;

  public UserQuery(DSLContext context,
                   TblUser tblUser){
    this.context = context;
    this.tblUser = tblUser;
  }

  /**
   * 아이디로 조회
   * @param id
   * @return
   */
  public Optional<Record> selectUserById(String id) {
    SelectConditionStep select = context.select().from(tblUser).where();
    select.and(tblUser.id.eq(id));
    return select.fetchOptional();
  }

  /**
   * 아이디 중복 개수 조회
   * @param id
   * @return
   */
  public Integer selectCountById(String id) {
    SelectConditionStep<?> select = context.selectCount().from(tblUser).where();
    select.and(tblUser.id.eq(id));
    return select.fetchOneInto(Integer.class);
  }

  /**
   * 휴대폰번호 중복 개수 조회
   * @param phone
   * @return
   */
  public Integer selectCountByPhone(String phone) {
    SelectConditionStep<?> select = context.selectCount().from(tblUser).where();
    select.and(tblUser.phone.eq(phone));
    return select.fetchOneInto(Integer.class);
  }
}
