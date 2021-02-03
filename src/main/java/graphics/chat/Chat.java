package graphics.chat;

import graphics.Config;
import graphics.Input;
import graphics.Rectangle;
import logic.Entity;
import java.util.ArrayList;

import static graphics.Config.MOUSE_SCROLL_SPEED;

public class Chat extends Entity {

	// zmienne
	private Rectangle chatRect;
	private Rectangle messagesRect;
	private Rectangle background;
	private ArrayList<Message> messages;
	private MessageInput messageInput;
	private float y = Config.SEARCH_Y;
	boolean isChatActive;
	boolean isNewMessage;

	// stale
	public final static String CHAT_DEFAULT = "chat/chat.png";
	public final static String CHAT_NEW = "chat/chat_new.png";

	public Chat() {
		isChatActive = false;
		// chatRect
		chatRect = new Rectangle(0.9f, 0.8f, 0.1f, 0.2f);
		chatRect.initGL(CHAT_DEFAULT, "rectangle.vert.glsl", "rectangle.frag");

		// messagesRect
		messagesRect = new Rectangle(Config.SEARCH_X, -1.1f, 1.0f, 2.2f);
		messagesRect.initGL("gui/blue.png","rectangle.vert.glsl", "rectangle.frag");
		messagesRect.setAlpha(Config.SEARCH_RECT_ALPH);

		// messages
		messages = new ArrayList<>();

		// messageInput
		messageInput = new MessageInput();

		// background
		background = new Rectangle(-1.0f, -1.0f, 2.0f, 2.0f);
		background.initGL("gui/black.png", "rectangle.vert.glsl", "rectangle.frag");
		background.setAlpha(0.7f);
	}

	public void addMessage(int id, String context) {

		// new message
		Message message = new Message(id, context);
		messages.add(0, message);

		// update messages
		float xInit = -0.9f;
		float yInit = -0.3f;
		for(Message m: messages) {
			m.moveTo(xInit, yInit);
			yInit += Config.MESSAGE_HEIGHT - 0.1f;
		}

		// update clipart
		if (!isNewMessage && !isChatActive) {
			isNewMessage = true;
			chatRect.setTexture(CHAT_NEW);
		}
	}

	@Override
	public void update() {
		// chat clipart
		if (chatRect.hasPoint(Input.MOUSE_X, Input.MOUSE_Y))
			isChatActive = !isChatActive;

		// chat
		if (isChatActive) {
			for (Message m : messages) m.move(0.0f, -Input.MOUSE_SCROLL_Y*MOUSE_SCROLL_SPEED);
			messageInput.update();
		}

		// clipart
		if (isChatActive && isNewMessage) {
			isNewMessage = false;
			chatRect.setTexture(CHAT_DEFAULT);
		}
	}

	@Override
	public void draw() {
		// chat
		if (isChatActive) {
			background.draw();
			messagesRect.draw();
			for(Message m: messages)
				if (messagesRect.collidesWith(m.getRectangle())) m.draw();
			messageInput.draw();
		}

		// chat clipart
		chatRect.draw();
	}

	public boolean isChatActive() {
		return isChatActive;
	}
}
