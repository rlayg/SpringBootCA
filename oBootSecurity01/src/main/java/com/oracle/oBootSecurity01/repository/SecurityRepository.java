package com.oracle.oBootSecurity01.repository;

import com.oracle.oBootSecurity01.model.User;

public interface SecurityRepository {
	void	save(User user);
	User	findByUsername(String username);
	
}
