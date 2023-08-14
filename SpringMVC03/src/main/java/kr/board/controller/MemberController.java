package kr.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.board.entity.Member;
import kr.board.mapper.MemberMapper;

@Controller
public class MemberController {

	@Autowired
	MemberMapper memberMapper;
	
	@RequestMapping("/memberJoin.do")
	public String memJoin() {
		return "member/join";
	}
	@RequestMapping("/memRegisterCheck.do") //리스푼스바디 -> ajax쪽으로 응답보내려면
	public @ResponseBody int memRegisterCheck(@RequestParam("memID") String memID) {
		Member m = memberMapper.registerCheck(memID);
		if(m != null || memID.equals("")) {
			return 0; //이미 존재하는 회원, 입력불가
		}
		return 1; //사용가능한 아이디
	}
	
	//회원가입처리
	@RequestMapping("/memRegister.do")
	public String memRegister(Member m, String memPassword1, String memPassword2,
			RedirectAttributes rttr, HttpSession session) {
		if(m.getMemID()==null || m.getMemID().equals("") ||
			memPassword1==null || memPassword1.equals("")||
		    memPassword2==null || memPassword2.equals("")||
			m.getMemName()==null || m.getMemName().equals("") ||
			m.getMemAge()==0 ||
			m.getMemGender()==null || m.getMemGender().equals("") ||
			m.getMemEmail()==null || m.getMemEmail().equals("") ) {
			//누락메세지를 가지고 가기? => jsp로 가는게 아니라서 객체바인딩을 못함(객체바인딩은 jsp로 갈때 모델에 setAttribute로 꺼내가는것)
			rttr.addFlashAttribute("msgType","실패메세지");
			rttr.addFlashAttribute("msg","모든 내용을 입력하세요.");
			return "redirect:/memberJoin.do"; //${msgType},${msg} -> EL로 받는다.
		}
		if(!memPassword1.equals(memPassword2)) {
			rttr.addFlashAttribute("msgType","실패메세지");
			rttr.addFlashAttribute("msg","비밀번호가 서로 다릅니다.");
			return "redirect:/memberJoin.do"; //${msgType},${msg} -> EL로 받는다.
		}
		m.setMemProfile(""); //사진이미지는 없다는 의미로 null->"";
		//누락된게 없으면 회원을 테이블에 저장하기.
		int result = memberMapper.register(m);
		if(result==1) { //회원가입 성공 메세지
			rttr.addFlashAttribute("msgType","성공메세지");
			rttr.addFlashAttribute("msg","회원가입을 축하드립니다.");
			//회원가입이 성공하면 -> 로그인처리 하기(회원가입성공과 동시에 로그인이 되도록 하는것)
			session.setAttribute("mvo", m); //회원가입성공은 m에 모든값이 정상적으로 들어갔다는 뜻이므로 Member객체 m을 세션으로 연결해서 넣어줌
			//session.setAttribute("mvo", m); 이 코드를 거쳤다는 것은 회원가입에 성공했다는 의미
			//그래서 jsp쪽에서 ${!empty mvo} 이런식으로 체크를 하면 회원가입을 햇는지 안했는지 체크가 가능하다.
			return "redirect:/"; // "/"-> index.jsp 첫페이지로 돌아가는것임.
		}else {
			rttr.addFlashAttribute("msgType","실패메세지");
			rttr.addFlashAttribute("msg","이미 존재하는 회원입니다. ");
			return "redirect:/memberJoin.do"; //${msgType},${msg} -> EL로 받는다.
		}
	}
	//로그아웃
	@RequestMapping("/memLogout.do")
	public String memLogout(HttpSession session) {
		session.invalidate();//세션 무효화
		return "redirect:/";
	}
	//로그인 화면으로 이동
	@RequestMapping("/memLoginForm.do")
	public String memLoginForm() {
		return "member/memLoginForm"; //memLoginForm.jsp
	}
	//로그인 기능 구현
	@RequestMapping("/memLogin.do")
	public String memLogin(Member m, RedirectAttributes rttr,HttpSession session) {
		if(m.getMemID()==null || m.getMemID().equals("") ||
			m.getMemPassword()==null || m.getMemPassword().equals("")) {
			rttr.addFlashAttribute("msgType", "실패메세지");
			rttr.addFlashAttribute("msg", "모든 입력칸을 입력해주세요.");
			return "redirect:/memLoginForm.do";
		}
		Member mvo = memberMapper.memLogin(m);
		if(mvo!=null) { //로그인에 성공 
			rttr.addFlashAttribute("msgType", "성공메세지");
			rttr.addFlashAttribute("msg", "로그인에 성공했습니다.");
			session.setAttribute("mvo", mvo);
			return "redirect:/";
		}else { //로그인 실패
			rttr.addFlashAttribute("msgType", "실패메세지");
			rttr.addFlashAttribute("msg", "아이디 혹은 비밀번호가 맞지않습니다. 다시 로그인 해주세요.");
			return "redirect:/memLoginForm.do";
		}
	}
	//회원정보 수정화면
	@RequestMapping("/memUpdateForm.do")
	public String memUpdateForm() {
		
		return "member/memUpdateForm";
	}
	
	//회원 정보 수정 기능
	@RequestMapping("/memUpdate.do")
	public String memUpdate(Member m , RedirectAttributes rttr, String memPassword1, String memPassword2, HttpSession session) {
		
		if(m.getMemID()==null || m.getMemID().equals("") ||
				memPassword1==null || memPassword1.equals("")||
			    memPassword2==null || memPassword2.equals("")||
				m.getMemName()==null || m.getMemName().equals("") ||
				m.getMemAge()==0 ||
				m.getMemGender()==null || m.getMemGender().equals("") ||
				m.getMemEmail()==null || m.getMemEmail().equals("") ) {
				//누락메세지를 가지고 가기? => jsp로 가는게 아니라서 객체바인딩을 못함(객체바인딩은 jsp로 갈때 모델에 setAttribute로 꺼내가는것)
				rttr.addFlashAttribute("msgType","실패메세지");
				rttr.addFlashAttribute("msg","모든 내용을 입력하세요.");
				return "redirect:/memUpdateForm.do"; //${msgType},${msg} -> EL로 받는다.
			}
		
			if(!memPassword1.equals(memPassword2)) {
				rttr.addFlashAttribute("msgType","실패메세지");
				rttr.addFlashAttribute("msg","비밀번호가 서로 다릅니다.");
				return "redirect:/memUpdateForm.do"; //${msgType},${msg} -> EL로 받는다.
			}
			
			//회원을 수정 저장하기
			int result = memberMapper.memUpdate(m);
			if(result==1) { //회원정보 수정 성공 메세지
				rttr.addFlashAttribute("msgType","성공메세지");
				rttr.addFlashAttribute("msg","회원정보 수정에 성공하였습니다.");
				//회원정보 수정에 성공하면 -> 로그인상태 유지
				session.setAttribute("mvo", m); //회원가입성공은 m에 모든값이 정상적으로 들어갔다는 뜻이므로 Member객체 m을 세션으로 연결해서 넣어줌
				return "redirect:/"; // "/"-> index.jsp 첫페이지로 돌아가는것임.
			}else {
				rttr.addFlashAttribute("msgType","실패메세지");
				rttr.addFlashAttribute("msg","회원정보 수정에 실패했습니다.");
				return "redirect:/memUpdateForm.do"; 
			}
	}
	
	//회원의 사진등록 화면
	@RequestMapping("/memImageForm.do")
	public String memImageForm() {
		
		return "member/memImageForm";
	}
	
	
	
}
