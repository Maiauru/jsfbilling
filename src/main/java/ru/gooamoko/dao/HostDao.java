package ru.gooamoko.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ru.gooamoko.model.Host;



public class HostDao {
	
	private SessionFactory factory;
	private Session session;
	
	private void begin() {
		session = factory.getCurrentSession();
		session.beginTransaction();
		}
	
	private void commit() {
		session.getTransaction().commit();
	}
	

	public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	
	public HostDao() {
		// Empty constructor
	}
	
	public HostDao(SessionFactory factory) {
		this.factory = factory;
	}
	
	@SuppressWarnings("unchecked")
	public List<Host> fetchAll() {
		begin();
		List<Host> list = session.createQuery("from Host").list();
		commit();
		return list;
	}

}
