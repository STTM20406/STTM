package kr.or.ddit.board.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.service.IBoardService;

@Controller
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Resource(name ="boardService")
	private IBoardService boardService;
	
	
	@RequestMapping(path = "/boardAdd", method = RequestMethod.GET)
	public String insertBoard(Model model) {
		
		List<BoardVo> boardList = boardService.boardList();
		
		model.addAttribute("boardList",boardList);
		
		return "/board/boardManager.adm.tiles";
	}
	
	@RequestMapping(path = "/boardAdd", method =RequestMethod.POST)
	public String insertBoard(Model model, String use_fl, String name, HttpServletRequest request) {
		String viewName="";
		
		logger.debug("!@# use_fl : {}",use_fl);
		logger.debug("!@# name : {}",name);
		
		BoardVo boardVo = new BoardVo();
		boardVo.setUse_fl(use_fl);
		boardVo.setName(name);
		
		int boardCnt = boardService.insertBoard(boardVo);
		
		if(boardCnt == 1) {
			viewName ="redirect:/boardAdd";
		}else{
			viewName ="redirect:/boardAdd";
		}
		
		ServletContext sc = request.getServletContext();
		List<BoardVo> boardList = boardService.boardListYes();
		sc.setAttribute("admBoardListY", boardList);
		
		return viewName;
	}
	
	@RequestMapping(path="/boardModify", method=RequestMethod.POST)
	public String updateBoard(Model model,int board_id02, String use_fl02, String name02, HttpServletRequest request ) {
		String viewName="";
		
		logger.debug("!@# board_id02 : {}",board_id02);
		logger.debug("!@# use_fl : {}",use_fl02);
		logger.debug("!@# name : {}",name02);
		
		BoardVo boardVo = new BoardVo();
		boardVo.setUse_fl(use_fl02);
		boardVo.setName(name02);
		boardVo.setBoard_id(board_id02);
		
		int updateCnt = boardService.updateBoard(boardVo);
		
		if(updateCnt==1) {
			viewName = "redirect:/boardAdd";
		}else {
			viewName = "redirect:/boardAdd";
			
		}
		
		ServletContext sc = request.getServletContext();
		List<BoardVo> boardList = boardService.boardListYes();
		sc.setAttribute("admBoardListY", boardList);
		
		
		return viewName;
	}
	
	
}
