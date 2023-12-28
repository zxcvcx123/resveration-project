package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.context.DelegatingApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.http.HttpSession;


//시큐리티 핵심 기능들을 컨트롤 하는 클래스
@Configuration
@EnableWebSecurity
public class SercurityConfig {
		
	@Bean
	public BCryptPasswordEncoder encodePwd() { //패스워드를 암호화 해주는 시큐리티 매서드 중 하나 이거해야 패스워드가 암호화됨
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain mySecurityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable();

		 http
		.authorizeHttpRequests()
		.requestMatchers(new AntPathRequestMatcher("/home/**")).authenticated() //해당 url주소로 들어오면 인증이 필요해
		.requestMatchers(new AntPathRequestMatcher("/reservation/**")).authenticated() //해당 url주소로 들어오면 인증이 필요해
		.requestMatchers(new AntPathRequestMatcher("/reservation_status/**")).authenticated() //해당 url주소로 들어오면 인증이 필요해
		.requestMatchers(new AntPathRequestMatcher("/myreservation/**")).authenticated() //해당 url주소로 들어오면 인증이 필요해
		.requestMatchers(new AntPathRequestMatcher("/request/**")).authenticated() //해당 url주소로 들어오면 인증이 필요해
		.requestMatchers(new AntPathRequestMatcher("/welcome/**")).authenticated() //해당 url주소로 들어오면 인증이 필요해
		.requestMatchers(new AntPathRequestMatcher("/noticeWrite")).authenticated() //해당 url주소로 들어오면 인증이 필요해
		.requestMatchers(new AntPathRequestMatcher("/noticeWriteDo")).authenticated() //해당 url주소로 들어오면 인증이 필요해
		.requestMatchers(new AntPathRequestMatcher("/notice/deleteNotice/**")).authenticated() //해당 url주소로 들어오면 인증이 필요해
		.requestMatchers(new AntPathRequestMatcher("/manager/**")).access(new WebExpressionAuthorizationManager("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')"))//manager 쪽으로 들어올려면 어드민 or 매니저 권한이 있는 사람만 들어와 / hasAnyRole 은 여러권한
		.requestMatchers(new AntPathRequestMatcher("/admin/**")).access(new WebExpressionAuthorizationManager("hasRole('ROLE_ADMIN')")) //admin은 어드민 권한 있는 사람만 들어와 / hasRole은 단일 권한
		//anyRequest()모든 리퀘스트에 대한 / permitAll() 하면 위에 따로 권한 설정한 페이지 외에는 전부 접속 가능 
		//기본 로그인 페이지가 노출되지 않고 바로 접근이 가능 / authenticated() = 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
		.anyRequest().permitAll()
		.and()
		.formLogin()
		.usernameParameter("userId")
		.passwordParameter("userPw")
		.loginPage("/login") // 기본 로그인 페이지말고 내가 지정한 로그인 페이지로 이동 loginPage("매핑주소") / disable() 로그인 페이지로 이동 x
		.loginProcessingUrl("/loginDo") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행 해준다. *컨트롤러에서 login을 따로 안만들어도 된다.
		.defaultSuccessUrl("/reservation"); //로그인 성공시 기본적으로 이동하는 url을 설정
		 
		http.logout()
			.logoutUrl("/logout")//로그아웃 처리 URL ( = from action url)
			.logoutSuccessUrl("/login") //로그아웃 성공 후 targetUrl, logoutSuccessHandler가 있다면 효과 없으므로 주석처리.
			.addLogoutHandler((request, response, authentication) -> {
				//굳이 내가 세션 무효화하지 않아도 됨.
				//LogoutFilter가 내부적으로 처리
				HttpSession session = request.getSession();
				if(session != null) {
					session.invalidate();
				}
			}) //로그아웃 핸들러 추가
			
			.logoutSuccessHandler((request, respone, authentication)-> {
				respone.sendRedirect("/login");
			}) // 로그아웃 성공핸들러
			.deleteCookies("remember-me");
			return http.build();
	}
	
}