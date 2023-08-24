package kr.bit.service;

import java.util.List;

import kr.bit.entity.Board;
import kr.bit.entity.Member;

public interface BoardService { 
	
	//비즈니스로직처리할 메서드 컨트롤러에서 서
	public List<Board> getList();	
	public Member login(Member vo);
	public void register(Board vo);
	public Board get(int idx);
	public void modify(Board vo);
	public void remove(int idx);
	public void replyProcess(Board vo);
}
