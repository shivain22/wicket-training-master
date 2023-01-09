package com.gel.wicket_training.hibernate;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.gel.wicket_training.entities.Person;
import com.gel.wicket_training.entities.PersonAddress;
import com.gel.wicket_training.entities.PersonBankAccount;
import com.gel.wicket_training.entities.PersonEmail;
import com.gel.wicket_training.entities.PersonMobileNumber;

public class HibernateUtil {
  private static SessionFactory sessionFactory;

  
  public static SessionFactory getSessionFactory() {
      if (sessionFactory == null) {
          try {
              Properties settings = new Properties();
              
              settings.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
              settings.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3305/wicket_training?useSSL=false");
              settings.put(Environment.USER, "root");
              settings.put(Environment.PASS, "");
              settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
              settings.put(Environment.SHOW_SQL, "true");
              settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
              settings.put(Environment.HBM2DDL_AUTO, "update");
              ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                  .applySettings(settings).build();
              Metadata metadata = new MetadataSources(standardRegistry)
              .addAnnotatedClass(Person.class)
              .addAnnotatedClass(PersonMobileNumber.class)
              .addAnnotatedClass(PersonEmail.class)
              .addAnnotatedClass(PersonAddress.class)
              .addAnnotatedClass(PersonBankAccount.class)
              .getMetadataBuilder()
              .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
              .build();
              sessionFactory = metadata.getSessionFactoryBuilder().build();
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
      return sessionFactory;
  }
  
  public static void shutdown() {
    getSessionFactory().close();
  }
}