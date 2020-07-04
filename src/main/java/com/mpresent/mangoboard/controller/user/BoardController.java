package com.mpresent.mangoboard.controller.user;


import com.mpresent.mangoboard.common.dto.ResultDTO;
import com.mpresent.mangoboard.common.exception.CustomException;
import com.mpresent.mangoboard.service.user.BoardService;
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

  BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping(value = "/")
  public ResultDTO getBoards(@RequestParam Integer page,
                             @RequestParam Integer limit,
                             HttpServletRequest request, HttpServletResponse response) throws CustomException {
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
