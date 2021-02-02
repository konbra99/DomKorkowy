package graphics.chat;

import graphics.Config;
import graphics.Engine;
import graphics.Rectangle;
import graphics.gui.Button;
import graphics.gui.TextArea;
import logic.Entity;

public class MessageInput extends Entity {

	private Rectangle inputRect;
	private Rectangle messageRect;
	private TextArea textArea;
	private float charWidth;
	private float charHeight;
	private Button sendButton;


	public MessageInput() {

		charWidth = 0.06f;
		charHeight = 0.16f;

		float x = Config.SEARCH_X+0.05f;
		float y = -0.95f;

		inputRect = new Rectangle(Config.SEARCH_X, -1.1f, 1.0f, 0.5f);
		inputRect.initGL("gui/blue.png","rectangle.vert.glsl", "rectangle.frag");

		messageRect = new Rectangle(x, y, 0.9f-0.12f, 0.3f);
		messageRect.initGL("chat/message.png","rectangle.vert.glsl", "rectangle.frag");

		textArea = new TextArea(x + 0.12f, y+0.05f, 0.74f-0.12f, 0.2f,
				charWidth, charHeight,
				"msgothic.bmp", 0.0f, 0.0f, 0.0f, 1.0f);
		textArea.getRectangle().setAlpha(0.0f);

		sendButton = new Button(Config.SEARCH_X + 0.85f, y, 0.15f, 0.3f, null, Button.SEND);
		sendButton.action = ()-> {
			String message = textArea.Clear();
			if (!message.isEmpty()) Engine.client.sendMessage(message);
		};
	}

	@Override
	public void draw() {
		inputRect.draw();
		messageRect.draw();
		textArea.draw();
		sendButton.draw();
	}

	@Override
	public void update() {
		textArea.update();
		sendButton.update();
	}
}
