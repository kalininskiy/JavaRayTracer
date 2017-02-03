package vdm.ivanhoe.raytracer.interfaces;

import vdm.ivanhoe.raytracer.classes.Intersection;
import vdm.ivanhoe.raytracer.classes.Ray;
import vdm.ivanhoe.raytracer.classes.Vector;

/**
 *  Thing
 */
public interface Thing {
    Intersection intersect(Ray ray);
    Vector normal(Vector pos);
    Surface surface = null;

    Surface getSurface();
}
