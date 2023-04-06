package com.oracle.oBootSecurity01.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootSecurity01.model.User;
import com.oracle.oBootSecurity01.repository.JpaSecurityRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
	private	final JpaSecurityRepository jpaSecurityRepository;
	
	@Override
	public User findByUsername(String username) {
		User user = jpaSecurityRepository.findByUsername(username);
		return user;
	}

	@Override
	public void save(User user) {
		jpaSecurityRepository.save(user);
		return;
	}

}
