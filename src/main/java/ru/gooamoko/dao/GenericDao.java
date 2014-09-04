package ru.gooamoko.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class GenericDao {

	private SessionFactory factory;
	protected Session session;

	protected void begin() {
		session = factory.getCurrentSession();
		session.beginTransaction();
	}

	protected void commit() {
		if (session.isOpen()) {
			session.getTransaction().commit();
		}
	}

	protected void rollback() {
		if (session.isOpen()) {
			session.getTransaction().rollback();
		}
	}

  public GenericDao() {
		this.factory = HibernateProvider.getSessionFactory();
	}

  public SessionFactory getFactory() {
    return factory;
  }

  public void setFactory(SessionFactory factory) {
    this.factory = factory;
  }
}
