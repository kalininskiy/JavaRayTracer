package vdm.ivanhoe.raytracer.classes;

/**
 *  Camera
 */
public class Camera {
    private Vector forward;
    private Vector right;
    private Vector up;
    private Vector pos;

    public Camera(Vector pos, Vector lookAt) {
        Vector down = new Vector(0.0, -1.0, 0.0);
        this.forward = Vector.normal(Vector.minus(lookAt, pos));
        this.right = Vector.times(1.5, Vector.normal(Vector.cross(this.forward, down)));
        this.up = Vector.times(1.5, Vector.normal(Vector.cross(this.forward, this.right)));
        this.pos = pos;
    }

    public Vector getForward() {
        return forward;
    }

    public Vector getRight() {
        return right;
    }

    public Vector getUp() {
        return up;
    }

    public Vector getPos() {
        return pos;
    }
}
