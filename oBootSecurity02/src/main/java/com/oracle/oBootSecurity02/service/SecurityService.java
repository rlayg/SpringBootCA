package com.oracle.oBootSecurity02.service;

import com.oracle.oBootSecurity02.entity.User;

public interface SecurityService {
	User findByUsername(String username);
	void save(User user);
}
