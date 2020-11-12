package constants;

public class JsonSerializationStatus {

	// TODO new
	public final static int ENTITY_OK = 0;
	public final static int STAGE_OK = 0;
	public final static int NOTEXISTING_PROPERTY = -1;
	public final static int NOTEXISTING_NAME = -2;

	// TODO old
	public final static int MAP_OK = 0;
	public final static int MAP_PROPERTIES_NULL = -1;
	public final static int MAP_STAGES_NULL = -2;
	public final static int MAP_READ_ERROR = -3;
	public final static int MAP_WRITE_ERROR = -4;
}
