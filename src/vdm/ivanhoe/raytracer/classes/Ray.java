package vdm.ivanhoe.raytracer.classes;

/**
 *  Ray
 */
public class Ray {
    private Vector start;
    private Vector dir;

    public Ray(Vector start, Vector dir) {
        this.start = start;
        this.dir = dir;
    }

    public Vector getStart() {
        return start;
    }

    public Vector getDir() {
        return dir;
    }
}
