package vdm.ivanhoe.raytracer.classes;

import vdm.ivanhoe.raytracer.interfaces.Thing;

/**
 *  Scene
 */
public class Scene {
    private Thing[] things;
    private Light[] lights;
    private Camera camera;

    public Scene(Thing[] things, Light[] lights, Camera camera) {
        this.things = things;
        this.lights = lights;
        this.camera = camera;
    }

    public Thing[] getThings() {
        return things;
    }

    public Light[] getLights() {
        return lights;
    }

    public Camera getCamera() {
        return camera;
    }
}
