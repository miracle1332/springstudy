package kr.board.entity;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;

@Data
public class MemberUser extends User{ //아이디, 패스워드, 권한정보를 가지고 있는 클래스

	private Member member;
	
	public MemberUser(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		
		super(username, password, authorities);
	}
	
	public MemberUser(Member mvo) {
		super(mvo.getMemID(), mvo.getMemPassword(), mvo.getAuthList().stream()
				.map(auth-> new SimpleGrantedAuthority(auth.getAuth())).
				collect(Collectors.toList()));
		this.member = mvo;
		// List<**AuthVO> ---> Collection<**SimpleGrantedAuthority>타입으로 넣어야함.
	}
}
