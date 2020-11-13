package constants;

public class JsonSerializationStatus {

	// TODO new
	public final static int ENTITY_OK = 0;
	public final static int STAGE_OK = 0;
	public final static int MAP_OK = 0;
	public final static int FILE_OK = 0;
	public final static int NONEXISTENT_PROPERTY = -1;
	public final static int NONEXISTENT_NAME = -2;
	public final static int NONEXISTENT_FILE = -3;
	public final static int EXISTENT_FILE = -4;
	public final static int PARSING_ERROR = -5;
	public final static int IO_ERROR = -6;
	public final static int INVALID_PROPERTY = -7;

	// TODO old
	public final static int MAP_PROPERTIES_NULL = -1;
	public final static int MAP_STAGES_NULL = -2;
	public final static int MAP_READ_ERROR = -3;
	public final static int MAP_WRITE_ERROR = -4;
}
