package com.oracle.oBootSecurity02.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootSecurity02.entity.User;
import com.oracle.oBootSecurity02.repository.JpaSecurityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {
	private final JpaSecurityRepository jpaSecurityRepository;	// Service니까 DAO 연결시켜줘야해

	@Override
	public User findByUsername(String username) {
		User user = jpaSecurityRepository.findByUsername(username);
		return user;
	}

	@Override
	public void save(User user) {
		System.out.println("SecurityServiceImpl save start.. ");
		jpaSecurityRepository.save(user);
		return;

	}

}
