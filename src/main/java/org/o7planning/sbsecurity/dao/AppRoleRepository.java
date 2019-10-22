package org.o7planning.sbsecurity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.o7planning.sbsecurity.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AppRoleRepository {

	private final EntityManager entityManager;

	public AppRoleRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<String> getRoleNames(Long userId) {
		String sql = "Select ur.appRole.roleName from " + UserRole.class.getName() + " ur "
				+ " where ur.appUser.userId = :userId ";

		Query query = this.entityManager.createQuery(sql, String.class);
		query.setParameter("userId", userId);

		return query.getResultList();
	}

}