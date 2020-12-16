package database;

import com.google.gson.JsonObject;
import map.MapManager;
import map.json.JsonUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MapsConnector {

	public static List<String> getMaps() {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		List<String> maps = session.createQuery("SELECT src from MapsEntity", String.class).list();

		transaction.commit();
		session.close();
		return maps;
	}

	public static MapsEntity getMap(int id) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		MapsEntity map = session.get(MapsEntity.class, id);
		transaction.commit();
		session.close();

		return map;
	}

//	public static void addMap(MapManager map) {
//		Session session = HibernateUtils.getSessionFactory().openSession();
//		Transaction transaction = session.beginTransaction();
//
//		try {
//			MapsEntity mapEntity = new MapsEntity();
//			mapEntity.setName(map.mapName);
//			mapEntity.setAuthor(map.author);
//			mapEntity.setDescription(map.description);
//			mapEntity.setNumOfStages(map.stages.size());
//			mapEntity.setSrc(map.toJson().toString());
//			session.save(mapEntity);
//			transaction.commit();
//		}
//		catch(Exception exception) {
//			transaction.rollback();
//			throw exception;
//		}
//		finally {
//			session.close();
//		}
//	}

	public static boolean addMap(String src) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		JsonObject obj = JsonUtils.fromString(src);

		try {
			MapsEntity mapEntity = new MapsEntity();
			mapEntity.setName(obj.get("mapName").getAsString());
			mapEntity.setAuthor(obj.get("author").getAsString());
			mapEntity.setNumOfStages(obj.get("stages").getAsJsonArray().size());
			mapEntity.setTime(obj.get("time").getAsInt());
			if (obj.has("description"))
				mapEntity.setDescription(obj.get("description").getAsString());


			mapEntity.setSrc(src);
			session.save(mapEntity);
			transaction.commit();
			return true;
		}
		catch(Exception exception) {
			transaction.rollback();
			exception.printStackTrace();
			return false;
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
//			JsonObject obj = JsonUtils.fromFile("test_file100.json");
//			map.fromJson(obj);
//			addMap(map);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		// ADD PLAY
//		addPlay(6);
//		addPlay(6);
//		addPlay(6);

		// GET MAPS
	}

}
