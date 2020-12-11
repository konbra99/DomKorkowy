package database;

import com.google.gson.JsonObject;
import map.MapManager;
import map.json.JsonUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MapcConnector {

	public static void getMaps() {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		List<MapsEntity> maps = session.createQuery("from MapsEntity").list();
		System.out.println(maps);
		System.out.println(maps.size());
		transaction.commit();
		session.close();
	}

	public static MapsEntity getMap(int id) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		MapsEntity map = session.get(MapsEntity.class, id);
		transaction.commit();
		session.close();

		return map;
	}

	public static void addMap(MapManager map) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		try {
			MapsEntity mapEntity = new MapsEntity();
			mapEntity.setName(map.mapName);
			mapEntity.setAuthor(map.author);
			mapEntity.setDescription(map.description);
			mapEntity.setNumOfStages(map.stages.size());
			mapEntity.setSrc(map.toJson().toString());
			session.save(mapEntity);
			transaction.commit();
		}
		catch(Exception exception) {
			transaction.rollback();
			throw exception;
		}
		finally {
			session.close();
		}
	}

	public static void addPlay(int id) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		try {
			MapsEntity mapEntity = session.get(MapsEntity.class, id);
			mapEntity.setNumOfPlays(mapEntity.getNumOfPlays()+1);
			session.save(mapEntity);
			transaction.commit();
		}
		catch(Exception exception) {
			transaction.rollback();
			throw exception;
		}
		finally {
			session.close();
		}
	}

	public static void main(String[] args) {
		// GET MAP
//		System.out.println(getMap(0));
//		System.out.println(getMap(1));
//		System.out.println(getMap(2));
//		System.out.println(getMap(3));

		// ADD MAP
//		try {
//			MapManager map = new MapManager();
//			JsonObject obj = JsonUtils.fromFile("test_file.json");
//			map.fromJson(obj);
//			addMap(map);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		// ADD PLAY
//		addPlay(6);
//		addPlay(6);
//		addPlay(6);
	}

}
