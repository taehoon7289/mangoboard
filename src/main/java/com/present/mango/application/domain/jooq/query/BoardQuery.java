package com.present.mango.application.domain.jooq.query;

import com.present.mango.application.domain.jooq.generate.tables.TblBoardMaster;
import com.present.mango.application.domain.jooq.generate.tables.TblUserMaster;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class BoardQuery {

  protected DSLContext context;
  protected TblBoardMaster tblBoardMaster;
  protected TblUserMaster tblUserMaster;

  /**
   * 게시물 리스트 가져오기
   * @param params
   * @return
   */
  public Result selectPageBoardAndUser(Map params, Pageable pageable) {
    SelectConditionStep select = context.select()
            .from(tblBoardMaster)
            .join(tblUserMaster)
            .on(tblBoardMaster.userNo.eq(tblUserMaster.userNo))
            .where();
    select = setPageCondition(select, params);
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
            .from(tblBoardMaster)
            .join(tblUserMaster)
            .on(tblBoardMaster.userNo.eq(tblUserMaster.userNo))
            .where();
    select = setPageCondition(select, params);
    return select.fetchOneInto(Integer.class);
  }

  /**
   * 게시물 가져오기
   * @param params
   * @return
   */
  public Optional<Record> selectRecordBoard(Map params) {
    SelectConditionStep select = context.select()
            .from(tblBoardMaster)
            .where();
    select = setRecordCondition(select, params);
    select.orderBy(tblBoardMaster.boardNo.desc());
    return select.fetchOptional();
  }

  /**
   * Page 조건 설정
   * @param select
   * @param params
   * @return
   */
  protected SelectConditionStep setPageCondition(SelectConditionStep select, Map params) {
    if ((Integer) params.getOrDefault("userNo", -1) > 0) {
      select.and(tblBoardMaster.userNo.eq((Integer) params.get("userNo")));
    }
    if ((Integer) params.getOrDefault("boardNo", -1) > 0) {
      select.and(tblBoardMaster.boardNo.eq((Integer) params.get("boardNo")));
    }
    if (!ObjectUtils.isEmpty(params.getOrDefault("searchText", ""))) {
      select.and(tblBoardMaster.title.contains((String) params.get("searchText"))
              .or(tblBoardMaster.contents.contains((String) params.get("searchText"))));
    }
    return select;
  }

  /**
   * Record 조건 설정
   * @param select
   * @param params
   * @return
   */
  protected SelectConditionStep setRecordCondition(SelectConditionStep select, Map params) {
    if ((Integer) params.getOrDefault("boardNo", -1) > 0) {
      select.and(tblBoardMaster.boardNo.eq((Integer) params.get("boardNo")));
    }
    return select;
  }

}
