package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.MainMapper;

//SercurityConfig에서 .loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어 있는 UserDetails loadUserByUsername 실행 
//이 Service를 안만들면 작동안됨 왜 ? 이렇게 하기로 규칙을 정했으니깐~
@Service
public class PrincipalDetailsService implements UserDetailsService{
	
	@Autowired
	private MainMapper mainMapper; //리포지토리(DAO, Mapper)에 userid 있는지 확인해야 하니깐 의존성 추가

	//시큐리티 session = Authentication = UserDetails = PrincipalDetails(userEntity) 
	//시큐리티 session(Authentication(PrincipalDetails(userEntity))); 이런 형식으로 시큐리티 session 안으로 들어가는거임
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		 //log
	    System.out.println("@@@@@@@@@@@@접속 ID: " + userId);
	    
	    UserDTO id = mainMapper.searchId(userId);;	    //userId가 null 이 아니면 리턴
	    if(id != null) {
	        return new PrincipalDetails(id);
	    }
	    return null;
	}
	//암호는 어디서 리턴하나요 ? 암호는 알아서 내부적으로 userid 매칭검증해서 처리해준다

	
}