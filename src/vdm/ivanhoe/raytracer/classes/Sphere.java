package vdm.ivanhoe.raytracer.classes;

import vdm.ivanhoe.raytracer.interfaces.Surface;
import vdm.ivanhoe.raytracer.interfaces.Thing;

/**
 *  Sphere
 */
public class Sphere implements Thing {
    private double radius2;
    private Vector center;
    private Surface surface;

    public Sphere(Vector center, double radius, Surface surface) {
        this.radius2 = radius * radius;
        this.center = center;
        this.surface = surface;
    }

    public Surface getSurface() {
        return surface;
    }

    public Vector normal(Vector pos) {
        return Vector.normal(Vector.minus(pos, this.center));
    }

    public Intersection intersect(Ray ray) {
        Vector eo = Vector.minus(this.center, ray.getStart());
        double v = Vector.dot(eo, ray.getDir());
        double dist = 0;
        if (v >= 0) {
            double disc = this.radius2 - (Vector.dot(eo, eo) - v * v);
            if (disc >= 0) {
                dist = v - Math.sqrt(disc);
            }
        }
        if (dist == 0) {
            return null;
        } else {
            return new Intersection(this, ray, dist);
        }
    }
}
