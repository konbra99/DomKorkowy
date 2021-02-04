package graphics.gui.gameend;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import graphics.Engine;
import graphics.Input;
import graphics.Rectangle;
import graphics.gui.Button;
import graphics.gui.Context;
import graphics.gui.GameplayContext;
import map.json.JsonUtils;

import java.util.ArrayList;

import static graphics.Config.MOUSE_SCROLL_SPEED;
import static logic.constants.MultiModes.DEATHMATCH;

public class GameEndContext extends Context {

	// zmienne
	String title;
	Rectangle titleBackground;
	ArrayList<GameEndAbstractRow> rows;
	Button backButton;
	float y;
	boolean status;

	private final static float charWidth = 0.09f;
	private final static float charHeight = 0.15f;
	private final static GameEndDataRow EMPTY_ROW = new GameEndDataRow("", "");
	private final static String BACKGROUND_WIN = "win.png";
	private final static String BACKGROUND_LOST = "lost.png";



	public GameEndContext() {
		super("background/gameend.png");
		init();
		rows = new ArrayList<>();

		// BACK
		backButton = new Button(-0.95f, -0.95f, 0.20f, 0.4f, null, Button.LEFT_ARROW);
		backButton.setText("BACK", "msgothic.bmp", 0.04f, 0.1f);
		backButton.action = () -> {
			Engine.activeContext = Engine.menu;
			Engine.STATE = Engine.GAME_STATE.MENU;
		};

		// BACKGROUND
		titleBackground = new Rectangle(-1.0f, 0.5f, 2.0f, 0.5f);
		titleBackground.initGL("gui/blue.png","rectangle.vert.glsl", "rectangle.frag");
	}

	@Override
	public void draw() {
		// background
		super.draw();

		float x = 0.0f - (charWidth*title.length()/2);
		float y = 0.6f;

		for(GameEndAbstractRow r: rows) r.draw();
		backButton.draw();

		titleBackground.draw();
		Engine.fontLoader.renderText(title, "msgothic.bmp", x, y, charWidth, charHeight,1.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	public void update() {
		for (GameEndAbstractRow r : rows) r.move(-Input.MOUSE_SCROLL_Y*MOUSE_SCROLL_SPEED);
		backButton.update();
	}

	public void refresh(String lobby) {
		JsonObject obj = JsonUtils.fromString(lobby);
		int game_mode = obj.get("game_mode").getAsInt();
		int winner = obj.get("winner").getAsInt();

		if (game_mode == DEATHMATCH)
			status = (winner == Engine.client.id);
		else
			status = (winner == GameplayContext.KORKOWY.getTeam());

		if (status) {
			rectangle.setTexture(BACKGROUND_WIN);
			title = "WYGRALES";
		}
		else {
			rectangle.setTexture(BACKGROUND_LOST);
			title = "PRZEGRALES";
		}

		rows.clear();
		y = 0.0f;

		// wspolne dane
		addRow(new GameEndDataRow("czas:", obj.get("time").getAsInt()));
		addRow(new GameEndDataRow("l. graczy:", obj.get("total_players").getAsInt()));
		addRow(EMPTY_ROW);

		if (game_mode == DEATHMATCH) {
			// DEATH MATCH
			addRow(new GameEndPlayerRow("PLAYER", "TEAM", "KILLS", "DEATHS"));
			JsonArray array = obj.get("clients").getAsJsonArray();
			for (JsonElement e: array) addRow(new GameEndPlayerRow(e.getAsJsonObject()));
		}
		else {
			// TEAMS & COOPERATION
			addRow(new GameEndDataRow("DRUZYNA 1", ""));
			addRow(new GameEndPlayerRow("PLAYER", "TEAM", "KILLS", "DEATHS"));
			JsonArray team1 = obj.get("team1").getAsJsonArray();
			for (JsonElement e: team1) addRow(new GameEndPlayerRow(e.getAsJsonObject()));

			addRow(EMPTY_ROW);
			addRow(new GameEndDataRow("DRUZYNA 2", ""));
			addRow(new GameEndPlayerRow("PLAYER", "TEAM", "KILLS", "DEATHS"));
			JsonArray team2 = obj.get("team2").getAsJsonArray();
			for (JsonElement e: team2) addRow(new GameEndPlayerRow(e.getAsJsonObject()));
		}
	}

	public void addRow(GameEndAbstractRow row) {
		rows.add(row);
		row.moveTo(y);
		y -= 0.18f;
	}
}
