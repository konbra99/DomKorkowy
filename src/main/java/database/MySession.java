package database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class MySession {

	private static org.hibernate.SessionFactory buildSessionFactory() {
		Configuration configObj = new Configuration();
		configObj.configure("hibernate.cfg.xml");
		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure().build();
		Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
		return metadata.getSessionFactoryBuilder().build();
	}

	public static void displayRecords() {
		SessionFactory sessionFactory = buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		List<MapsEntity> maps = session.createQuery("from MapsEntity").list();
		System.out.println(maps);
		System.out.println(maps.size());
		session.getTransaction().commit();
		session.close();
	}

	public static void main(String[] args) {
		displayRecords();
	}
}
