package kr.bit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.bit.entity.Board;
import kr.bit.mapper.BoardMapper;
import kr.bit.service.BoardService;

@Controller //POJO
@RequestMapping("board/*")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@GetMapping("/list")
	public String getList(Model model) {//객체바인딩을 위한 model객체		
		List<Board> list = boardService.getList();
		//list의 저장값을 객체 바인딩
		model.addAttribute("list",list);
		return "board/boardList"; //list를 jsp로 포워딩
	}
}
