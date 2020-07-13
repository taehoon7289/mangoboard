package com.present.mango.jooq.query;

import com.present.mango.jooq.generate.tables.TblUserMaster;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserQuery {

  protected DSLContext context;
  protected TblUserMaster tblUserMaster;

  /**
   * 아이디로 조회
   * @param id
   * @return
   */
  public Optional<Record> selectUserById(String id) {
    SelectConditionStep select = context.select().from(tblUserMaster).where();
    select.and(tblUserMaster.id.eq(id));
    return select.fetchOptional();
  }

  /**
   * 아이디 중복 개수 조회
   * @param id
   * @return
   */
  public Integer selectUserCountById(String id) {
    SelectConditionStep<?> select = context.selectCount().from(tblUserMaster).where();
    select.and(tblUserMaster.id.eq(id));
    return select.fetchOneInto(Integer.class);
  }

  /**
   * 휴대폰번호 중복 개수 조회
   * @param phone
   * @return
   */
  public Integer selectUserCountByPhone(String phone) {
    SelectConditionStep<?> select = context.selectCount().from(tblUserMaster).where();
    select.and(tblUserMaster.phone.eq(phone));
    return select.fetchOneInto(Integer.class);
  }
}
