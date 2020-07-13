package com.present.mango.jooq.query;

import com.present.mango.jooq.generate.tables.TblBoard;
import com.present.mango.jooq.generate.tables.TblUser;
import com.present.mango.jooq.generate.tables.daos.TblBoardDao;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.Map;


@Repository
public class BoardQuery {

  protected DSLContext context;
  protected TblBoard tblBoard;
  protected TblUser tblUser;
  protected TblBoardDao tblBoardDao;

  public BoardQuery(DSLContext context,
                    TblBoard tblBoard,
                    TblUser tblUser,
                    TblBoardDao tblBoardDao){
    this.context = context;
    this.tblBoard = tblBoard;
    this.tblUser = tblUser;
    this.tblBoardDao = tblBoardDao;
  }

  /**
   * 게시물 리스트 가져오기
   * @param params
   * @return
   */
  public Result selectPageBoardAndUser(Map params, Pageable pageable) {
    SelectConditionStep select = context.select()
            .from(tblBoard)
            .join(tblUser)
            .on(tblBoard.userNo.eq(tblUser.userNo))
            .where();
    select = setCondition(select, params);
    select.limit(pageable.getOffset(),pageable.getPageSize());
    return select.fetch();
  }

  /**
   * 게시물 리스트 Cnt
   * @param params
   * @return
   */
  public Integer selectPageBoardAndUserCnt(Map params) {
    SelectConditionStep<?> select = context
            .selectCount()
            .from(tblBoard)
            .join(tblUser)
            .on(tblBoard.userNo.eq(tblUser.userNo))
            .where();
    select = setCondition(select, params);
    return select.fetchOneInto(Integer.class);
  }

  /**
   * 게시물 가져오기
   * @param params
   * @return
   */
  public Record selectBoard(Map params) {
    SelectConditionStep select = context.select()
            .from(tblBoard)
            .where();
    select = setCondition(select, params);
    select.orderBy(tblBoard.boardNo.desc());
    return select.fetchAny();
  }

  /**
   * 조건 설정
   * @param select
   * @param params
   * @return
   */
  protected SelectConditionStep setCondition(SelectConditionStep select, Map params) {
    if ((Integer) params.getOrDefault("userNo", -1) > 0) {
      select.and(tblBoard.userNo.eq((Integer) params.get("userNo")));
    }
    if ((Integer) params.getOrDefault("boardNo", -1) > 0) {
      select.and(tblBoard.boardNo.eq((Integer) params.get("boardNo")));
    }
    if (!ObjectUtils.isEmpty(params.getOrDefault("searchText", ""))) {
      select.and(tblBoard.title.contains((String) params.get("searchText"))
              .or(tblBoard.contents.contains((String) params.get("searchText"))));
    }
    return select;
  }

}
