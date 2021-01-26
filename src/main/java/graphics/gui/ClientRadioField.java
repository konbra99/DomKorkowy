package graphics.gui;

import graphics.Config;
import graphics.Engine;

public class ClientRadioField extends DataField {
	private int id = -1;
	private boolean empty = true;
	private boolean status = false;


	public ClientRadioField(String [] textures) {
		super(0.0f, 0.0f, Config.MAP_BUTTON_WIDTH, Config.MAP_BUTTON_HEIGHT, null, textures);
		this.action = ()->{};
		text = "EMPTY";
	}

	@Override
	public void draw() {
		if (empty)
			this.rectangle.setTexture(texture_disabled);
		else if (status)
			this.rectangle.setTexture(texture_enabled);
		else
			this.rectangle.setTexture(texture_invalid);

		rectangle.draw();
		if (this.text.length() > 0 && this.font.length() > 0) {
			Engine.fontLoader.renderText(text, font, text_x, text_y, charwidth, charheight,
					0.0f, 0.0f, 0.0f, 1.0f);
		}
	}

	@Override
	public void move(float dx, float dy) {
		super.move(dx, dy);
		this.text_x = this.rectangle.posX + 0.1f;
		this.text_y = (this.rectangle.posY + (this.rectangle.height / 2) - (charheight / 2))* Config.RESOLUTION;
	}

	public boolean join(int id) {
		System.out.println("JOIN" + id);
		if (empty) {
			// pusty slot
			this.id = id;
			this.empty = false;
			this.text = Integer.toString(id);
			return true;
		}
		else {
			// niepusty slot
			return false;
		}
	}

	public boolean exit(int id) {
		if (this.id == id) {
			// wlasciciel slotu
			this.empty = true;
			this.id = -1;
			return true;
		}
		else {
			// nie wlasciciel slotu
			return false;
		}
	}
	public boolean status(int id, boolean status) {
		if (this.id == id) {
			// wlasciciel slotu
			this.status = status;
			return true;
		}
		else {
			// nie wlasciciel slotu
			return false;
		}
	}

	@Override
	public Integer getAsInteger() {
		return id;
	}
}
