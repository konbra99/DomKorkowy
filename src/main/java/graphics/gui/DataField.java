package graphics.gui;

import map.MapManager;

public class DataField extends Button{
	public DataField(float x, float y, float width, float height, Action action) {
		super(x, y, width, height, action);
	}

	public DataField(float x, float y, float width, float height, Action action, String[] textures) {
		super(x, y, width, height, action, textures);
	}

	public boolean getValue(MapManager map) {
		return false;
	}

	public Object getAsObject() { return null; }
	public Float getAsFloat() { return null; }
	public Integer getAsInteger() { return null; }
	public Integer getAsPositiveInteger() { return null; }
	public String getAsString() { return null; }
	public String getAsNonemptyString() { return null; }

}
