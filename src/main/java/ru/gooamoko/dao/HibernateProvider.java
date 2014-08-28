package ru.gooamoko.dao;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateProvider {

  private static final SessionFactory factory = buildFactory();

  public static SessionFactory getSessionFactory() {
    return factory;
  }

  private static SessionFactory buildFactory() {
    try {
      Configuration configuration = new Configuration();
      configuration.configure();
      ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(
          configuration.getProperties()).build();
      return configuration.buildSessionFactory(registry);
    } catch (HibernateException ex) {
      // Make sure you log the exception, as it might be swallowed
      System.err.println("Initial SessionFactory creation failed." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }
}
