package kr.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import kr.board.security.MemberUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService memberUserDetailsService() {
		return new MemberUserDetailsService();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberUserDetailsService()).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//요청에 대한 설정
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.addFilterBefore(filter, CsrfFilter.class);
		//**요청에 따른 권한을 확인하여 서비스하는 부분. - 1
		http //어떤권한을 가지고 있는 사람한테만 보여줄 것인지
			.authorizeRequests()
				.antMatchers("/")
				.permitAll()
				.and()//다음권한 또 걸고 시플때
			.formLogin()
				.loginPage("/memLoginForm.do")
				.loginProcessingUrl("/memLogin.do") //이 url이 왓을때 스프링 내부 api를 거치겠다는 뜻
				.permitAll()
				.and()
			.logout()
				.invalidateHttpSession(true)
				.logoutSuccessUrl("/") //메인jsp
				.and()
			.exceptionHandling().accessDeniedPage("/access-denied"); //권한이 없는 페이지
	}
	
	//패스워드 인코딩 객체(비밀번호 암호화하기 - 객체화가 필요함.)
	@Bean
	public PasswordEncoder passwordEncoder() { //평문을 암호화코드로 바꿔주는거
		return new BCryptPasswordEncoder();
	}
	
}
