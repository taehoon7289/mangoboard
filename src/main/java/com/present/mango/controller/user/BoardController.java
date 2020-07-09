package com.present.mango.controller.user;


import com.present.mango.dto.ResultDTO;
import com.present.mango.common.exception.CustomException;
import com.present.mango.common.token.JwtTokenProvider;
import com.present.mango.form.board.BoardSaveForm;
import com.present.mango.service.user.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/user/boards")
public class BoardController {

  BoardService boardService;
  JwtTokenProvider jwtTokenProvider;

  BoardController(BoardService boardService,
                  JwtTokenProvider jwtTokenProvider) {
    this.boardService = boardService;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @GetMapping(value = "/")
  public ResultDTO getBoards(@RequestHeader(value = "X-Auth-Token") String token,
                             @RequestParam Integer page,
                             @RequestParam Integer limit,
                             HttpServletRequest request, HttpServletResponse response) throws CustomException {
    log.info("token getData :: {}", jwtTokenProvider.getData(token));
    List result = boardService.getBoards(page,limit,request,response);
    return new ResultDTO(1,"",result);
  }

  @PostMapping(value = "/")
  public ResultDTO setBoard(@RequestHeader(value = "X-Auth-Token") String token,
                            @RequestBody @Valid BoardSaveForm boardSaveForm) throws CustomException {
    log.info("token getData :: {}", jwtTokenProvider.getData(token));
    Map data = jwtTokenProvider.getData(token);
    Integer userNo = (Integer) data.getOrDefault("userNo", -1);
    Integer result = boardService.postBoard(userNo,boardSaveForm);
    return new ResultDTO(1,"",result);
  }

}
