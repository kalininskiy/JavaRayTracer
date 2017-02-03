package vdm.ivanhoe.raytracer.classes;

import vdm.ivanhoe.raytracer.interfaces.Surface;
import vdm.ivanhoe.raytracer.interfaces.Thing;

/**
 *  Plane
 */
public class Plane implements Thing {
    private Vector norm;
    private double offset;
    private Surface surface;

    public Plane(Vector norm, double offset, Surface surface) {
        this.norm = norm;
        this.offset = offset;
        this.surface = surface;
    }

    public Surface getSurface() {
        return surface;
    }

    public Vector normal(Vector pos) {
        return Vector.normal(pos);
    }

    public Intersection intersect(Ray ray) {
        double denom = Vector.dot(norm, ray.getDir());
        if (denom > 0) {
            return null;
        } else {
            double dist = (Vector.dot(norm, ray.getStart()) + offset) / (-denom);
            return new Intersection(this, ray, dist);
        }
    }
}
