package com.oracle.oBootSecurity01.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.oracle.oBootSecurity01.model.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaSecurityRepository implements SecurityRepository {
	private final EntityManager em;
	
	@Override
	public void save(User user) {
		em.persist(user);
		return;
	}

	@Override
	public User findByUsername(String username) {
		User user = em.createQuery("select u from u where username = :username ", User.class)
					  .setParameter("username", username)
					  .getSingleResult()
					;
		return user;
	}

}
