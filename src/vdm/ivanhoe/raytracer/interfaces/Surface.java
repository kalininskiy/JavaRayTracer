package vdm.ivanhoe.raytracer.interfaces;

import vdm.ivanhoe.raytracer.classes.Color;
import vdm.ivanhoe.raytracer.classes.Vector;

/**
 *  Surface
 */
public interface Surface {
    Color diffuse(Vector pos);
    Color specular(Vector pos);
    double reflect(Vector pos);
    double roughness = 0;

    double getRoughness();
}
