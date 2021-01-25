package server;

public class Protocol {

	private Protocol() {}

	// PROTOKOL					                    //      SEND BY CLIENT              SERVER RESPONSE
	public final static int GET_MAPS = 1;           //      GET_MAPS                    GET_MAPS num map1 map2 ...
	public final static int GET_MAP = 2;            //
	public final static int GET_ROOMS = 3;          //      GET_ROOMS                   GET_ROOMS num room1 room2 ...
	public final static int GET_ROOM = 4;           //
	public final static int GET_CLIENT_ID = 5;      //      GET_CLIENT_ID               GET_CLIENT_ID id

	public final static int CREATE_MAP = 10;        //      CREATE_MAP src              CREATE_MAP status
	public final static int CREATE_ROOM = 11;       //
	public final static int DELETE_MAP = 12;        //
	public final static int DELETE_ROOM = 13;
	public final static int PING = 14;              //      PING                        PING

	// LOBBY
	public final static int LOBBY_MY_JOIN = 50;     //      LOBBY_MY_JOIN lobby_id      LOBBY_MY_JOIN status
	public final static int LOBBY_MY_EXIT = 51;     //      LOBBY_MY_EXIT               ---
	public final static int LOBBY_MY_STATUS = 52;   //      LOBBY_MY_STATUS status      ---
	public final static int LOBBY_OTHER_JOIN = 53;  //      ---                         LOBBY_OTHER_JOIN id
	public final static int LOBBY_OTHER_EXIT = 54;  //      ---                         LOBBY_OTHER_EXIT id
	public final static int LOBBY_OTHER_STATUS = 55;//      ---                         LOBBY_OTHER_STATUS id status
	public final static int LOBBY_ADMIN = 56;       //      ---                         LOBBY_ADMIN
	public final static int LOBBY_READY = 57;       //      ---                         LOBBY_READY status
	public final static int LOBBY_CREATE = 58;      //      LOBBY_CREATE lobby          ---
	public final static int LOBBY_START = 59;       //      LOBBY_START                 LOBBY_START

	// MULTIPLAYER
	public final static int MULTI_MY_POSITION = 100;//      MULTI_MY_POSITION x y       ---
	public final static int MULTI_MY_STAGE = 101;   //      MULTI_MY_STAGE stage        ---

	// STATUS LOBBY
	public final static int LOBBY_NOT_EXIST = 1;
	public final static int LOBBY_IS_FULL = 2;
	public final static int LOBBY_JOINED = 3;
}
