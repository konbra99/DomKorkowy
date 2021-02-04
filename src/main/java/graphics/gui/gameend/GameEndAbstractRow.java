package graphics.gui.gameend;

public class GameEndAbstractRow {

	// zmienne
	protected float y;

	public void draw() {}

	public void move(float y) {
		this.y += y;
	}

	public void moveTo(float y) {
		this.y = y;
	}
}
