package com.mpresent.mangoboard.controller.user;


import com.mpresent.mangoboard.common.constant.exception.BoardConstException;
import com.mpresent.mangoboard.common.constant.exception.UserConstException;
import com.mpresent.mangoboard.common.dto.ResultDTO;
import com.mpresent.mangoboard.common.exception.CustomException;
import com.mpresent.mangoboard.common.util.PageData;
import com.mpresent.mangoboard.service.user.BoardService;
import com.mpresent.mangoboard.service.user.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/boards")
public class BoardController {

  BoardService boardService;

  BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping(value = "/")
  public ResultDTO getBoards(@RequestParam Optional<Integer> page,
                             @RequestParam Optional<Integer> limit,
                             HttpServletRequest request, HttpServletResponse response) throws CustomException {
    // 필수값 null 체크
    Integer ogPage = page.orElseGet(() -> 1);
    Integer ogLimit = limit.orElseGet(() -> 10);
    PageData pageData = boardService.getBoards(ogPage,ogLimit,request,response);
    return new ResultDTO(1,"",pageData);
  }

  @PostMapping(value = "/")
  public ResultDTO setBoard(@RequestParam Optional<String> title,
                            @RequestParam Optional<String> contents) throws CustomException {
    // 필수값 null 체크
    String ogTitle = title.orElseThrow(() -> new CustomException(BoardConstException.INVALID_BOARD_TITLE));
    String ogContents = contents.orElseThrow(() -> new CustomException(BoardConstException.INVALID_BOARD_CONTENTS));
    Integer result = boardService.setBoard(ogTitle,ogContents);
    return new ResultDTO(1,"",null);
  }

}
