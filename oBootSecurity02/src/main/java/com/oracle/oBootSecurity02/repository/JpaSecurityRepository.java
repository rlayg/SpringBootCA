package com.oracle.oBootSecurity02.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.oracle.oBootSecurity02.entity.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaSecurityRepository implements SecurityRepository {
    
	private final EntityManager em;
	
	@Override
	public void save(User user) {
		em.persist(user);

	}
    // username --> unique 라 가정 
	@Override
	public User findByUsername(String username) {
		User user  = em.createQuery("select u from User u where username = :username ", User.class)
		               .setParameter("username",  username)
		               .getSingleResult() 
	             ;
		return user;
	}

}
