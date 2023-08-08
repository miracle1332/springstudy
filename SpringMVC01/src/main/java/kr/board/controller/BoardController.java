package kr.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.board.entity.Board;
import kr.board.mapper.BoardMapper;

@Controller //이클래스가 컨트롤러구나 하는 식별의 의미
public class BoardController { // =new BoardController (우리가 하지 않지만 스프링컨테이너라는 메모리안에 보드컨트롤러라는 객체가 만들어짐.
		
	// boardList.do
	@Autowired //특정객체를 메모리를 필요하기때문에 주입을 받는 방법.DI DEPENDENCY INJECTION의존성주입
	private BoardMapper mapper;
	
	// HandlerMapping --> 클래스가 내부에 있어서 요청이 오면 어떤 메서드로 처리할껀지 내부적으로 연결해준다. 
	@RequestMapping("/boardList.do")
	public String boardList(Model model) { //model -> 객체바인딩
		List<Board> list = mapper.getLists();
		model.addAttribute("list",list);//객체바인딩****
		return "boardList"; //<-포워딩 경로/다음페이지는 이쪽 jsp로 넘어가면 된다는 즉, 뷰의 경로를 넘기는 것이다. /WEB-INF/views/  .jsp 로 포웢드
		//뷰의 전체 경로를 제공해주는 클래스 viewResolver클래스도 스프링에서 제공해준다.
	}
	
	@GetMapping("/boardForm.do")
	public String boardForm() {
		return "boardForm"; //WEB-INF/views/ boardForm.jsp 로 forward된닷.
	}
	
	@PostMapping("/boardInsert.do")
	public String boardInsert(Board vo) { //title, content, writer => 파라미터수집(Board) 보드에 수집!
		mapper.boardInsert(vo); //등록
		return "redirect:/boardList.do"; //redirect
	}
	
	@GetMapping("/boardContent.do") //넘어오는 파라미터이름이 받는 변수이름과 같으면 앞의 리퀘스트파람은 생략해도된다. 
	//public String boardContent(@RequestParam("idx") int  idx, Model model)이렇게 적어도 되지만 파람을 생략하여
	public String boardContent(int  idx, Model model) { // ?idx=6
		//조회수 증가
		mapper.boardCount(idx); //상세보기가 되면 조회수 증가
		Board vo = mapper.boardContent(idx);
		model.addAttribute("vo", vo); // ${vo.idx}
		return "boardContent";
	}
	
	@GetMapping("/boardDelete.do/{idx}")
	public String boardDelete(@PathVariable("idx") int idx) { //?idx=6 대신 /${}로 쓴다. 어노테이션으로 idx값을 받겠다는 의미
		mapper.boardDelete(idx); //삭제
		return "redirect:/boardList.do"; //redirect
	}
	
	@GetMapping("/boardUpdateForm.do/{idx}")
	public String boardUpdateForm(@PathVariable("idx") int idx, Model model) {
		Board vo = mapper.boardContent(idx);
		model.addAttribute("vo", vo);
		return "boardUpdate"; //boardUpdate.jsp
	}
	
	@PostMapping("/boardUpdate.do")
	public String boardUpdate(Board vo) { //idx, title, content
		mapper.boardUpdate(vo); //수정성공
		return "redirect:/boardList.do";
	}
}