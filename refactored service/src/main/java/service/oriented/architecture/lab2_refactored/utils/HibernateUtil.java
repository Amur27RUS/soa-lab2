package service.oriented.architecture.lab2_refactored.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import service.oriented.architecture.lab2_refactored.entity.Album;
import service.oriented.architecture.lab2_refactored.entity.Coordinates;
import service.oriented.architecture.lab2_refactored.entity.MusicBand;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
//                settings.put(Environment.URL, "jdbc:postgresql://pg/studs");
//                settings.put(Environment.USER, "s263890");
//                settings.put(Environment.PASS, "bws515");
                settings.put(Environment.URL, "jdbc:postgresql://localhost/postgres");
                settings.put(Environment.USER, "postgres");
                settings.put(Environment.PASS, "postgrePASS");

                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);


                configuration.addAnnotatedClass(MusicBand.class);
                configuration.addAnnotatedClass(Album.class);
                configuration.addAnnotatedClass(Coordinates.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                return sessionFactory;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
