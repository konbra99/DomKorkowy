package graphics.chat;

import logic.Entity;

public class Message extends Entity {
	private MessageAuthor author;
	private MessageContext context;

	public Message(int id, String context) {
		this.author = new MessageAuthor(id);
		this.context = new MessageContext(context);
	}

	@Override
	public void draw() {
		context.draw();
		author.draw();
	}

	public void move(float dx, float dy) {
		author.move(dx, dy);
		context.move(dx, dy);
	}

	public void moveTo(float x, float y) {
		author.moveTo(x, y);
		context.moveTo(x, y);
	}
}
