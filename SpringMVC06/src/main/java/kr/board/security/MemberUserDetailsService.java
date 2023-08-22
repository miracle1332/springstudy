package kr.board.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.board.entity.Member;
import kr.board.entity.MemberUser;
import kr.board.mapper.MemberMapper;

public class MemberUserDetailsService implements UserDetailsService{
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Override //패스워드까지 검사를 해주는 메서드 mapper클래스와 다리 역할. id를 가지고와서 검사.
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//로그인처리 하기
		Member mvo = memberMapper.memLogin(username);
		//-->userDeatails ---> implements->user ---> extends--->MemberUser
		if(mvo != null) { //해당사용자가 있다면
			return new MemberUser(mvo); //new MemberUser(mvo); //Member정보와 AuthVO정보 두개를 MemberUser에 넣어주기.
			//스프링 시큐리티는 내부 필터를 통해야하기 때문에 바로 리턴하지 못해서 MemberUser클래스를 만들어야함.
		}else {
			throw new UsernameNotFoundException("user with usernema" + username + "not exist...");
		}
	}

}
