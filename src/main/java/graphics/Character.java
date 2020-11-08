package graphics;

public class Character {
    public int hp;
    private String texture;
    public Rectangle shape;

    public Character(float a, float b, float c, float d, String texture) {
        this.shape = new Rectangle(a, b, c, d);
        this.texture = texture;
        shape.initGL(this.texture);
    }
}
