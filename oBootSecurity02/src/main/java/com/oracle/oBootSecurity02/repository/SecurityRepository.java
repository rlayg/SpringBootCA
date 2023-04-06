package com.oracle.oBootSecurity02.repository;

import com.oracle.oBootSecurity02.entity.User;

public interface SecurityRepository {
	void   save(User user);
	User   findByUsername(String username);
}
