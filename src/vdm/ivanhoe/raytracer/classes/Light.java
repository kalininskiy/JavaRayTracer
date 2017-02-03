package vdm.ivanhoe.raytracer.classes;

/**
 *  Light
 */
public class Light {
    private Vector pos;
    private Color color;

    public Light(Vector pos, Color color) {
        this.pos = pos;
        this.color = color;
    }

    public Vector getPos() {
        return pos;
    }

    public Color getColor() {
        return color;
    }
}
