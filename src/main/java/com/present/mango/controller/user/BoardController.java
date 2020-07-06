package com.present.mango.controller.user;


import com.present.mango.common.dto.ResultDTO;
import com.present.mango.common.exception.CustomException;
import com.present.mango.common.token.JwtTokenProvider;
import com.present.mango.service.user.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping(value = "/api/user/board")
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
    Page result = boardService.getBoards(page,limit,request,response);
    return new ResultDTO(1,"",result);
  }

  @PostMapping(value = "/")
  public ResultDTO setBoard(@RequestParam String title,
                            @RequestParam String contents) throws CustomException {
    Integer result = boardService.setBoard(title,contents);
    return new ResultDTO(1,"",result);
  }

}
