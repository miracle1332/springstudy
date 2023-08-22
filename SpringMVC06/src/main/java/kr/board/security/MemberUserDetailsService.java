package kr.board.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MemberUserDetailsService implements UserDetailsService{

	@Override //패스워드까지 검사를 해주는 메서드 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return null;
	}

}
