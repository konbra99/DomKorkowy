package temp_server;

public class TempServer {

	public static TempRoom[] getRooms() {
		TempRoom [] rooms = new TempRoom[3];
		rooms[0] = new TempRoom("nazwa0", "admin0", "mapa0", "12");
		rooms[1] = new TempRoom("nazwa1", "admin1", "mapa1", "123");
		rooms[2] = new TempRoom("nazwa2", "admin2", "mapa2", "999");
		return rooms;
	}
}
