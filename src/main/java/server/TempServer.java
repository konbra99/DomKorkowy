package server;

public class TempServer {

	public static Room[] getRooms() {
		Room[] rooms = new Room[3];
		rooms[0] = new Room(0, "nazwa0", "admin0", "mapa0", 12);
		rooms[1] = new Room(1, "nazwa1", "admin1", "mapa1", 123);
		rooms[2] = new Room(2, "nazwa2", "admin2", "mapa2", 999);
		return rooms;
	}
}
