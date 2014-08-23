package ru.gooamoko.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateProvider {
	private static SessionFactory factory;
	private static ServiceRegistry registry;

	public static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.configure();
		registry = new StandardServiceRegistryBuilder().applySettings(
				configuration.getProperties()).build();
		factory = configuration.buildSessionFactory(registry);
		return factory;
	}

}
