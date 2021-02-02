package graphics.chat;

import graphics.Config;
import graphics.Engine;
import logic.Entity;

public class MessageContext extends Entity {

	// zmienne
	private String [] lines;
	private float textX;
	private float textY;
	private float charWidth;
	private float charHeight;

	// stale
	public final static String MESSAGE_TEXTURE = "chat/message.png";

	public MessageContext(String context) {
		super(0.2f, 0.0f, Config.MESSAGE_WIDTH, Config.MESSAGE_HEIGHT, MESSAGE_TEXTURE);
		super.init();
		this.textX = 0.0f;
		this.textY = 0.0f;
		this.charWidth = 0.06f;
		this.charHeight = 0.16f;
		context = context.substring(0, Math.min(context.length(), 30));
		this.lines = context.split("(?<=\\G.{15})");
	}

	@Override
	public void draw() {
		rectangle.draw();

		// wiadomosc
		float offset = 0.0f;
		for (String s: lines) {
			Engine.fontLoader.renderText(s, "msgothic.bmp",
					textX, textY + offset,
					charWidth*0.7f, charHeight*0.7f,
					0.0f, 0.0f, 0.0f, 1.0f);
			offset -= 0.1f;
		}

	}

	public void move(float dx, float dy) {
		rectangle.move(dx, dy);
		textX = this.rectangle.posX + 0.12f;
		textY = (this.rectangle.posY + 0.07f) * Config.RESOLUTION;
	}


	public void moveTo(float x, float y) {
		this.rectangle.move(0.2f+x - this.rectangle.posX, y - this.rectangle.posY);
		textX = this.rectangle.posX + 0.12f;
		textY = (this.rectangle.posY + 0.07f) * Config.RESOLUTION;
		System.out.println(lines[0]);
	}

}
