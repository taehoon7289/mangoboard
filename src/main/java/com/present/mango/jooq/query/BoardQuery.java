package com.present.mango.jooq.query;

import com.present.mango.jooq.generate.tables.TblBoard;
import com.present.mango.jooq.generate.tables.TblUser;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;


@Component
public class BoardQuery {

  DSLContext context;
  TblBoard tblBoard;

  public BoardQuery(DSLContext context,
                    TblBoard tblBoard){
    this.context = context;
    this.tblBoard = tblBoard;
  }

  /**
   * 게시물 리스트 가져오기
   * @param params
   * @return
   */
  public Result selectBoards(Map params) {
    SelectConditionStep select = context.select()
            .from(tblBoard)
            .where();
//    select = setCondition(select, params);
    select.orderBy(tblBoard.boardNo.desc());
    return select.fetch();
  }

}
