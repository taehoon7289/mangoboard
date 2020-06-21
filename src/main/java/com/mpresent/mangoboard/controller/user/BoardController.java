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
  public ResultDTO getBoards(@RequestParam Integer page,
                             @RequestParam Integer limit,
                             HttpServletRequest request, HttpServletResponse response) throws CustomException {
    PageData pageData = boardService.getBoards(page,limit,request,response);
    return new ResultDTO(1,"",pageData);
  }

  @PostMapping(value = "/")
  public ResultDTO setBoard(@RequestParam String title,
                            @RequestParam String contents) throws CustomException {
    Integer result = boardService.setBoard(title,contents);
    return new ResultDTO(1,"",result);
  }

}
