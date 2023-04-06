package com.oracle.oBootSecurity02.configuration.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.oracle.oBootSecurity02.entity.User;

import lombok.Data;
//Authentication 객체에 저장할 수 있는 유일한 타입
//login이라는 주소가 호출이 되면, Security가 낚아 채서 login 진행
//login 진행 완료후 Security Session 생성 (Security ContextHolder에 정보 저장)
//Security Session -> Authentication -> UserDetails[PrincipalDetails]

@Data
public class PrincipalDetails implements UserDetails {
	
	private User  user;
	
	public PrincipalDetails(User user) {
		this.user = user;
       	System.out.println("PrincipalDetails 생성자 getUsername->"+user.getUsername());
 	}

	@Override
    // 해당 User의 권한을 리턴하는곳. 그냥 약속이래
	public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				System.out.println("getAuthorities getAuthority user.getRole()->"+user.getRole());
				return user.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 예시 ->1년동안 Login 안한 사람 휴먼 계정 ->return false 
		return true;
	}

}
