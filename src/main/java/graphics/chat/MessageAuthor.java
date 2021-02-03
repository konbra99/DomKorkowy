package graphics.chat;

import graphics.Config;
import graphics.Engine;
import logic.Entity;

public class MessageAuthor extends Entity {

	// zmienne
	String id;
	float textX;
	float textY;
	private float charWidth;
	private float charHeight;

	// stale
	public final static String AUTHOR_DEFAULT = "chat/author.png";

	public MessageAuthor(int id) {
		super(0.05f, 0.03f, 0.15f, 0.15f* Config.RESOLUTION, AUTHOR_DEFAULT);
		init();
		this.id = Integer.toString(id);
		this.textX = 0.0f;
		this.textY = 0.0f;
		this.charWidth = 0.06f;
		this.charHeight = 0.16f;
	}

	@Override
	public void draw() {
		rectangle.draw();
			Engine.fontLoader.renderText(id, "msgothic.bmp",
					textX+0.02f, textY+0.06f,
					charWidth*0.7f, charHeight*0.7f,
					1.0f, 1.0f, 1.0f, 1.0f);
	}

	public void move(float dx, float dy) {
		rectangle.move(dx, dy);
		this.textX = this.rectangle.posX + (this.rectangle.width / 2) - (charWidth * id.length() / 2);
		this.textY = (this.rectangle.posY + (this.rectangle.height / 2) - (charHeight / 2)) * Config.RESOLUTION;
	}

	public void moveTo(float x, float y) {
		this.rectangle.move(0.05f+x - this.rectangle.posX, y - this.rectangle.posY);
		this.textX = this.rectangle.posX + (this.rectangle.width / 2) - (charWidth * id.length() / 2);
		this.textY = (this.rectangle.posY + (this.rectangle.height / 2) - (charHeight / 2)) * Config.RESOLUTION;
	}
}
