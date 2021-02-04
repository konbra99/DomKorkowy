package graphics.gui.gameend;

import com.google.gson.JsonObject;
import graphics.Engine;

public class GameEndPlayerRow extends GameEndAbstractRow{

	// zmienne
	private String playerId;
	private String teamId;
	private String kills;
	private String deaths;

	// stale
	public final static float X_PLAYER = -0.9f;
	public final static float X_TEAM = -0.4f;
	public final static float X_KILLS = 0.1f;
	public final static float X_DEATHS = 0.6f;
	private final static float charWidth = 0.06f;
	private final static float charHeight = 0.1f;

	public GameEndPlayerRow(int playerId, int teamId, int kills, int deaths) {
		this.playerId = Integer.toString(playerId);
		this.teamId = Integer.toString(teamId);
		this.kills = Integer.toString(kills);
		this.deaths = Integer.toString(deaths);
		this.y = y;
	}

	public GameEndPlayerRow(String playerId, String teamId, String kills, String deaths) {
		this.playerId = playerId;
		this.teamId = teamId;
		this.kills = kills;
		this.deaths = deaths;
		this.y = y;
	}

	public GameEndPlayerRow(JsonObject obj) {
		this.playerId = Integer.toString(obj.get("id").getAsInt());
		this.teamId = Integer.toString(obj.get("team").getAsInt());
		this.kills = Integer.toString(obj.get("kills").getAsInt());
		this.deaths = Integer.toString(obj.get("deaths").getAsInt());
		this.y = y;
	}

	public void draw() {
		Engine.fontLoader.renderText(playerId, "msgothic.bmp", X_PLAYER, y, charWidth, charHeight,1.0f, 1.0f, 1.0f, 1.0f);
		Engine.fontLoader.renderText(teamId, "msgothic.bmp", X_TEAM, y, charWidth, charHeight,1.0f, 1.0f, 1.0f, 1.0f);
		Engine.fontLoader.renderText(kills, "msgothic.bmp", X_KILLS, y, charWidth, charHeight,1.0f, 1.0f, 1.0f, 1.0f);
		Engine.fontLoader.renderText(deaths, "msgothic.bmp", X_DEATHS, y, charWidth, charHeight,1.0f, 1.0f, 1.0f, 1.0f);
	}
}
