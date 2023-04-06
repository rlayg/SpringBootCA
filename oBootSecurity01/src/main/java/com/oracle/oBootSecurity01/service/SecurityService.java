package com.oracle.oBootSecurity01.service;

import com.oracle.oBootSecurity01.model.User;

public interface SecurityService {
	User findByUsername(String username);
	void save(User user);
}
