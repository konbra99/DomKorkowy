package graphics.gui.gameend;

import com.google.gson.JsonObject;
import graphics.Engine;

public class GameEndDataRow extends GameEndAbstractRow{

	// zmienne
	private String title;
	private String data;

	// stale
	public final static float X_TITLE = -0.9f;
	public final static float X_DATA = -0.2f;
	private final static float charWidth = 0.06f;
	private final static float charHeight = 0.1f;

	public GameEndDataRow(String title, String data) {
		this.title = title;
		this.data = data;
	}

	public GameEndDataRow(String title, int data) {
		this.title = title;
		this.data = Integer.toString(data);
	}

	public void draw() {
		Engine.fontLoader.renderText(title, "msgothic.bmp", X_TITLE, y, charWidth, charHeight,1.0f, 1.0f, 1.0f, 1.0f);
		Engine.fontLoader.renderText(data, "msgothic.bmp", X_DATA, y, charWidth, charHeight,1.0f, 1.0f, 1.0f, 1.0f);
	}
}
