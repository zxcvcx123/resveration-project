package com.example.demo.auth;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDTO;


//시큐리티로 로그인 할때 필요한 검증 및 기능들을 여기 클래스에서 담당
//시큐리티가 /login url로 요청이 오면 낚아채서 로그인을 진행
//로그인을 완료 하면 시큐리티 session을 만들어준다. (Security ContextHolder) 이 키값을 담아 세션 정보를 저장
//오브젝트가 정해져 있음 => Authentication 타입 객체임
//Authentication 안에 User 정보가 있어야 함
//User 오브젝트 타입 => UserDetails 타입 객체   <<< 이건 다 정해져 있는 거임 
/*
 * Securtiy Session => Authentication => UserDetails 
 * Securtiy Session안에 들어가야 하는 객체가 Authentication 객체이고 그 객체 안에 User 정보를 저장할 때에는 UserDetalis 객체여야함
 * 이건 정해진 룰임
*/

@Component
public class PrincipalDetails implements UserDetails {//UserDetails를 상속해 PrincipalDetails = UserDetails 타입이 된다 
														//PrincipalDetails를 Authentication 객체 안에 담을 수 있다.
	
	//참고로 상속받아서 그밑에 오버라이드 하나씩 다 타이핑 하지 말고 우클릭 -> source -> Override/implements methods 이용해라 정모야
	private UserDTO user;   //컴포지션(이거 나중에 봐도 잘 모르겠으면 gpt나 검색 ㄱㄱ
	
	public PrincipalDetails(UserDTO user) { //user 정보를 가져오는 메인 메소드를 만든다 이렇게 하면 PrincipalDetails 클래스는 User 클래스의 객체를 포함하게 되며 컴포지션 예시이다
		this.user = user;				//가져온 값을 this.user에 담는다
	}
	
	//해당 User의 권한을 리턴하는 곳!!
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>(); //ArrayList는 Collection의 자식이여서 이렇게 사용가능
		collect.add(new GrantedAuthority() { //User권한은 우리가 String로 해놔서 Collection 타입이 아니라서 바로 리턴이 안됨 그래서 해당 작업을 통해 변환
				@Override
				public String getAuthority() {
					return user.getRole(); //user 권한 리턴
				}
			});  //최종 정리하면 collect() 안에 add 기능 이용해서 (ArrayList로 객체를 생성했으니깐 사용 가능한거) 추가 시키는데 
				//String 타입으로 바로 변환이 안되니 new GrantedAuthority() 를 통해 문자열로 반환
			return collect; 
		}
		
		//Password 가져오기
		@Override
		public String getPassword() {
			
			return user.getPw();
		}
		
		//id 가져오기
		public String getUserid() {
			
			return user.getId();
		}
		
		//user 이름 가져오기
		public String getUsername() {
			
			return user.getName();  
		}
		
		//user 권한 가져오기
		public String getUserRole() {
			return user.getRole();
		}
		
		//계정이 만료 됐는지 확인하는 기능
		@Override
		public boolean isAccountNonExpired() {
			
			return true;
		}

		//계정이 잠겨 있는지 확인하는 기능
		@Override
		public boolean isAccountNonLocked() {
			
			return true;
		}
		
		//암호가 몇 일 지났는지 검증 / 이걸로 6개월 지났으면 비밀번호 변경해라 이런것도 가능
		@Override
		public boolean isCredentialsNonExpired() {
			
			return true;
		}
		
		//계정이 활성화 되어있는지 확인
		@Override
		public boolean isEnabled() {
			
			//구현 예시
			//우리 사이트에서 1년동안 회원이 로그인을 안하면 휴먼계정으로 하기로 했을 때
			//User 엔티티에 로그인 날짜가 있어야함 *그냥 이런것도 가능하다고 하는거임
			//현재시간 - 로그인시간 => 1년 초과하면 return false; 이런식으로 *지금 못함 엔티티에 안넣어서~
			
			return true;
		}
														
		

	
}