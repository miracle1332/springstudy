package kr.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import kr.board.entity.AuthVO;
import kr.board.entity.Member;
import kr.board.mapper.MemberMapper;

@Controller
public class MemberController {

	@Autowired
	MemberMapper memberMapper;
	
	@Autowired
	PasswordEncoder pwEncoder;

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
			m.getMemAge()==0 ||  m.getAuthList().size()==0 ||
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
		//추가 : 비밀번호를 암호화 하기(api) - api는 security.config에 저장
		String encyptPw = pwEncoder.encode(m.getMemPassword());
		m.setMemPassword(encyptPw);//암호화된 패스워드로 바꿔서 다시 저장해주기.
		int result = memberMapper.register(m);
		if(result==1) { //회원가입 성공 메세지
			// 추가 : 권한테이블에 회원의 권한을 저장하기
			List<AuthVO> list = m.getAuthList();
			for(AuthVO authVO : list) {
				if(authVO.getAuth() != null) { //널체크 한번더하기
					AuthVO saveVO = new AuthVO();
					saveVO.setMemID(m.getMemID()); //회원아이디ㅣ
					saveVO.setAuth(authVO.getAuth()); //회원의 권한
					memberMapper.authInsert(saveVO);
				}
			}
			rttr.addFlashAttribute("msgType","성공메세지");
			rttr.addFlashAttribute("msg","회원가입을 축하드립니다.");
			//회원가입이 성공하면 -> 로그인처리 하기(회원가입성공과 동시에 로그인이 되도록 하는것)
			//getmember() -> 회원정보 + 권한정보
			Member mvo = memberMapper.getMember(m.getMemID());
			//System.out.println(mvo);
			session.setAttribute("mvo", mvo); //회원가입성공은 m에 모든값이 정상적으로 들어갔다는 뜻이므로 Member객체 m을 세션으로 연결해서 넣어줌
			//session.setAttribute("mvo", m); 이 코드를 거쳤다는 것은 회원가입에 성공했다는 의미
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
		//추가 : 비밀번호 일치여부 체크
		//Member mvo = memberMapper.getMember(m.getMemID());
		//session.setAttribute("mvo", mvo); 
		if(mvo!=null && pwEncoder.matches(m.getMemPassword(), mvo.getMemPassword())) { //로그인에 성공 
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
				m.getMemAge()==0 || m.getAuthList().size()==0 ||
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
			//추가 : 비밀번호 암호화 추가
			String encyptPw = pwEncoder.encode(m.getMemPassword());
			m.setMemPassword(encyptPw);
			int result = memberMapper.memUpdate(m);
			if(result==1) { //회원정보 수정 성공 메세지
				//기존권한을 삭제하고
				List<AuthVO> list = m.getAuthList(); //권한리스트 가져오기
				for(AuthVO authVO : list) {
					if(authVO.getAuth()!=null) {
						memberMapper.authDelete(m.getMemID());
					}
				}
				//새로운 권한을 추가하기.
				for(AuthVO authVO : list) {
					if(authVO.getAuth()!=null) {
						AuthVO saveVO = new AuthVO();
						saveVO.setMemID(m.getMemID()); //회원아이디ㅣ
						saveVO.setAuth(authVO.getAuth()); //회원의 권한
						memberMapper.authInsert(saveVO);
					}
				}
				
				rttr.addFlashAttribute("msgType","성공메세지");
				rttr.addFlashAttribute("msg","회원정보 수정에 성공하였습니다.");
				//회원정보 수정에 성공하면 -> 로그인상태 유지
				Member mvo = memberMapper.getMember(m.getMemID());
				session.setAttribute("mvo", mvo); //회원가입성공은 m에 모든값이 정상적으로 들어갔다는 뜻이므로 Member객체 m을 세션으로 연결해서 넣어줌
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
	
	// 회원사진 이미지 업로드(proimg, DB저장)
		@RequestMapping("/memImageUpdate.do")
		public String memImageUpdate(HttpServletRequest request,HttpSession session, RedirectAttributes rttr) throws IOException {
			// 파일업로드 API(cos.jar, 3가지)
			MultipartRequest multi=null;
			int fileMaxSize=10*1024*1024; // 10MB		
			String savePath=request.getRealPath("resources/proimg"); // 1.png
			try {                                                                      												  // 1_1.png
				// 이미지 업로드
				multi=new MultipartRequest(request, savePath, fileMaxSize, "UTF-8",new DefaultFileRenamePolicy());
			
			} catch (Exception e) {
				e.printStackTrace();
				rttr.addFlashAttribute("msgType", "실패메세지");
				rttr.addFlashAttribute("msg", "파일의 크기는 10MB를 넘을 수 없습니다.");			
				return "redirect:/memImageForm.do";
			}
			//데이터베이스 테이블에 회원이미지를 업데이트
			String memID = multi.getParameter("memID");
			String newProfile = "";
			File file = multi.getFile("memProfile");
			if(file != null) { // 업로드가 된 상태(.png, .jpg, .gif)
				//이미지 파일 여부를 체크 -> 이미지 파일이 아니면 삭제(1.png) - 확장자 가져오기
				String ext = file.getName().substring(file.getName().lastIndexOf(".")+1); //파일 이름을 리턴
				ext = ext.toUpperCase(); // 확장자 명을 대문자로 바꿈 ( 대문자로 다 비교할고).
				if(ext.equals("PNG") || ext.equals("GIF") || ext.equals("JPG")) {
					//새로업로드된 이미지(new -> 1.png), 현재 db에 있는 이미지(old -> 4.png)
					String oldProfile = memberMapper.getMember(memID).getMemProfile();
					File oldFile = new File(savePath + "/" + oldProfile );
					if(oldFile.exists()) {
						oldFile.delete();
					}
					newProfile=file.getName();
				}else { //이미지 파일이 아니면
					if(file.exists()) { //파일이 존재하면
						file.delete(); //파일 삭제
					}
					rttr.addFlashAttribute("msgType", "실패메세지");
					rttr.addFlashAttribute("msg", "이미지 파일만 업로드 가능합니다.");			
					return "redirect:/memImageForm.do";
				}
			}
		//새로운 이미지를 DB 테이블에 저장하기(업데이트)
		Member mvo = new Member();	
		mvo.setMemID(memID);
		mvo.setMemProfile(newProfile);
		memberMapper.memProfileUpdate(mvo); //이미지 업데이트 성공
		Member m = memberMapper.getMember(memID);
		//**세션을 새롭게 생성(중요..)
		session.setAttribute("mvo", m);
		rttr.addFlashAttribute("msgType", "성공메세지");
		rttr.addFlashAttribute("msg", "프로필 사진 변경에 성공했습니다.");		
		return "redirect:/"; //메인으로가기 index.jsp
		}
}
