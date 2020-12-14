package server;

public class Protocol {

	private Protocol() {}

	public final static int GET_MAPS = 1;
	public final static int GET_MAP = 2;
	public final static int GET_ROOMS = 3;
	public final static int GET_ROOM = 4;

	public final static int SET_MAPS = -1;
	public final static int SET_MAP = -2;
	public final static int SET_ROOMS = -3;
	public final static int SET_ROOM = -4;
}
