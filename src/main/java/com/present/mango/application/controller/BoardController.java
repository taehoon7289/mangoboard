package com.present.mango.application.controller;


import com.present.mango.common.exception.CustomException;
import com.present.mango.common.token.JwtTokenProvider;
import com.present.mango.common.constant.dto.ResultDTO;
import com.present.mango.application.form.board.BoardSaveForm;
import com.present.mango.application.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@CrossOrigin("*")
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/user/boards")
public class BoardController {

  BoardService boardService;
  JwtTokenProvider jwtTokenProvider;

  @GetMapping(value = "/")
  public ResultDTO getBoardList(@RequestHeader(value = "X-Auth-Token", required = false) String token,
                                @PageableDefault(page = 0, size = 10) Pageable pageable,
                                @RequestParam(defaultValue = "") String searchText) {
//    Map data = jwtTokenProvider.getData(token);
//    Integer userNo = (Integer) data.getOrDefault("userNo", -1);
    Page result = boardService.getBoardList(pageable,searchText);
    return new ResultDTO(1,"",result);
  }

  @PostMapping(value = "/")
  public ResultDTO saveBoard(@RequestHeader(value = "X-Auth-Token") String token,
                             @ModelAttribute @Valid BoardSaveForm boardSaveForm) throws CustomException {
    log.info("test :: {}", boardSaveForm);
    Map data = jwtTokenProvider.getData(token);
    Integer userNo = (Integer) data.getOrDefault("userNo", -1);
    Integer result = boardService.saveBoard(userNo,boardSaveForm);
    return new ResultDTO(1,"",result);
  }

}
