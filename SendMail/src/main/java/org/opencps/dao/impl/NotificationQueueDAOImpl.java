package org.opencps.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.opencps.dao.NotificationQueueDAO;
import org.opencps.entity.NotificationQueue;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class NotificationQueueDAOImpl implements NotificationQueueDAO{

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager entityManager;
	@SuppressWarnings("unchecked")
	@Override
	public List<NotificationQueue> getQueueList() {
		String JQL = "SELECT n FROM NotificationQueue n WHERE n.expireDate < :now";
		System.out.println("entityManager: "+entityManager);
		Query q = entityManager.createQuery(JQL, NotificationQueue.class);
		q.setParameter("now", new java.sql.Date(new Date().getTime()));
		return q.getResultList();
	}

}
