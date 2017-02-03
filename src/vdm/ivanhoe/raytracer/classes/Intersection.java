package vdm.ivanhoe.raytracer.classes;

import vdm.ivanhoe.raytracer.interfaces.Thing;

/**
 *  Intersection
 */
public class Intersection {
    private Thing thing = null;
    private Ray ray = null;
    private double dist = 0;

    Intersection(Thing thing, Ray ray, double dist) {
        this.thing = thing;
        this.ray = ray;
        this.dist = dist;
    }

    public Thing getThing() {
        return thing;
    }

    public Ray getRay() {
        return ray;
    }

    public double getDist() {
        return dist;
    }
}
