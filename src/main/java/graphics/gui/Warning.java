package graphics.gui;

import graphics.Engine;
import graphics.Rectangle;


public class Warning extends Button {

	private final Rectangle exclamation;
	private final Rectangle background;

	public Warning() {
		super(-0.5f, -0.5f, 1.0f, 1.0f, null, Button.BIG_BUTTON);
		this.rectangle.setTexture(texture_disabled);
		is_visible = false;

		exclamation = new Rectangle(-0.5f, -0.3f, 0.4f, 0.5f);
		exclamation.initGL("gui/exclamation.png", "rectangle.vert.glsl", "rectangle.frag");

		background = new Rectangle(-1.0f, -1.0f, 2.0f, 2.0f);
		background.initGL("gui/black.png", "rectangle.vert.glsl", "rectangle.frag");
		background.setAlpha(0.7f);

		this.action = this::action;
	}

	@Override
	public void draw() {
		if (is_visible) {
			background.draw();
			rectangle.draw();
			exclamation.draw();
			float y = 0.13f;
			for (String str : text.split("(?<=\\G.{12})")) {
				Engine.fontLoader.renderText(str, "msgothic.bmp", -0.2f, y, 0.05f, 0.08f,
						0.0f, 0.0f, 0.0f, 1.0f);
				y -= 0.15f;
			}
		}
	}

	@Override
	public void update() {
		if (is_visible)
			super.update();
	}

	public void showWarning(String text) {
		this.text = text;
		is_visible = true;
	}

	private void action() {
		is_visible = false;
	}
}
