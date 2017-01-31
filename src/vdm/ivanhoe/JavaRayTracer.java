package vdm.ivanhoe;

import java.awt.*;

/**
 *  JavaRayTracer
 * (c) 2017, code by Ivan "VanDamM" Kalininskiy
 */
public class JavaRayTracer {

    /**
     *  Vector
     */
    public static class Vector {
        double x, y, z;

        public Vector(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public static Vector times(double k, Vector v) { return new Vector(k * v.x, k * v.y, k * v.z); }
        public static Vector minus(Vector v1, Vector v2) { return new Vector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z); }
        public static Vector plus(Vector v1, Vector v2) { return new Vector(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z); }
        public static double dot(Vector v1, Vector v2) { return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z; }
        public static double mag(Vector v) { return Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z); }

        public static Vector norm(Vector v) {
            double mag = mag(v);
            double div = (mag == 0) ? Double.POSITIVE_INFINITY : (1.0 / mag);
            return times(div, v);
        }

        public static Vector cross(Vector v1, Vector v2) {
            return new Vector(v1.y * v2.z - v1.z * v2.y,
                    v1.z * v2.x - v1.x * v2.z,
                    v1.x * v2.y - v1.y * v2.x);
        }
    }

    /**
     *  Color
     */
    public static class Color {
        double r, g, b;

        public Color(double r, double g, double b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public static Color scale(double k, Color v) { return new Color(k * v.r, k * v.g, k * v.b); }
        public static Color plus(Color v1, Color v2) { return new Color(v1.r + v2.r, v1.g + v2.g, v1.b + v2.b); }
        public static Color times(Color v1, Color v2) { return new Color(v1.r * v2.r, v1.g * v2.g, v1.b * v2.b); }
        public static Color white = new Color(1.0, 1.0, 1.0);
        public static Color grey = new Color(0.5, 0.5, 0.5);
        public static Color black = new Color(0.0, 0.0, 0.0);
        public static Color background = black;
        public static Color defaultColor = black;

        public static double legalize(double d) {
            return (d > 1) ? 1 : d;
        }

        public static java.awt.Color toDrawingColor(Color c) {
            return new java.awt.Color(
                    (int) Math.floor(legalize(c.r) * 255),
                    (int) Math.floor(legalize(c.g) * 255),
                    (int) Math.floor(legalize(c.b) * 255)
            );
        }
    }

    /**
     *  Camera
     */
    public class Camera {
        public Vector forward;
        public Vector right;
        public Vector up;
        public Vector pos;

        public Camera(Vector pos, Vector lookAt) {
            Vector down = new Vector(0.0, -1.0, 0.0);
            this.forward = Vector.norm(Vector.minus(lookAt, pos));
            this.right = Vector.times(1.5, Vector.norm(Vector.cross(this.forward, down)));
            this.up = Vector.times(1.5, Vector.norm(Vector.cross(this.forward, this.right)));
            this.pos = pos;
        }
    }

    /**
     *  Ray
     */
    public class Ray {
        public Vector start;
        public Vector dir;

        public Ray(Vector start, Vector dir) {
            this.start = start;
            this.dir = dir;
        }
    }

    /**
     *  Intersection
     */
    public class Intersection {
        public Thing thing = null;
        public Ray ray = null;
        public double dist = 0;

        public Intersection(Thing thing, Ray ray, double dist) {
            this.thing = thing;
            this.ray = ray;
            this.dist = dist;
        }
    }

    /**
     *  Surface
     */
    public interface Surface {
        Color diffuse(Vector pos);
        Color specular(Vector pos);
        double reflect(Vector pos);
        double roughness = 0;
    }

    /**
     *  Thing
     */
    public interface Thing {
        Intersection intersect(Ray ray);
        Vector normal(Vector pos);
        Surface surface = null;

        Surface getSurface();
    }

    /**
     *  Light
     */
    public class Light {
        public Vector pos;
        public Color color;

        public Light(Vector pos, Color color) {
            this.pos = pos;
            this.color = color;
        }
    }

    /**
     *  Scene
     */
    public class Scene {
        public Thing[] things;
        public Light[] lights;
        public Camera camera;

        public Scene(Thing[] things, Light[] lights, Camera camera) {
            this.things = things;
            this.lights = lights;
            this.camera = camera;
        }
    }

    /**
     *  Sphere
     */
    class Sphere implements Thing {
        public double radius2;
        public Vector center;
        public Surface surface;

        public Sphere(Vector center, double radius, Surface surface) {
            this.radius2 = radius * radius;
            this.center = center;
            this.surface = surface;
        }

        public Surface getSurface() {
            return surface;
        }

        public Vector normal(Vector pos) {
            return Vector.norm(Vector.minus(pos, this.center));
        }

        public Intersection intersect(Ray ray) {
            Vector eo = Vector.minus(this.center, ray.start);
            double v = Vector.dot(eo, ray.dir);
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

    /**
     *  Plane
     */
    class Plane implements Thing {
        Vector norm;
        double offset;
        Surface surface;

        public Plane(Vector norm, double offset, Surface surface) {
            this.norm = norm;
            this.offset = offset;
            this.surface = surface;
        }

        public Surface getSurface() {
            return surface;
        }

        public Vector normal(Vector pos) {
            return Vector.norm(pos);
        }

        public Intersection intersect(Ray ray) {
            double denom = Vector.dot(norm, ray.dir);
            if (denom > 0) {
                return null;
            } else {
                double dist = (Vector.dot(norm, ray.start) + offset) / (-denom);
                return new Intersection(this, ray, dist);
            }
        }
    }

    /**
     *  Surfaces
     */
    public class Surfaces {
        public class shiny implements Surface {
            public Color diffuse(Vector pos) { return Color.white; }
            public Color specular(Vector pos) { return Color.grey; }
            public double reflect(Vector pos) { return 0.7; }
            public double roughness = 250;
        }

        public class checkerboard implements Surface {
            public Color diffuse(Vector pos) {
                if ((Math.floor(pos.z) + Math.floor(pos.x)) % 2 != 0) {
                    return Color.white;
                } else {
                    return Color.black;
                }
            }
            public Color specular(Vector pos) { return Color.white; }
            public double reflect(Vector pos) {
                if ((Math.floor(pos.z) + Math.floor(pos.x)) % 2 != 0) {
                    return 0.1;
                } else {
                    return 0.7;
                }
            }
            public double roughness = 150;
        }
    }

    /**
     *  RayTracer
     */
    public class RayTracer {
        private int maxDepth = 5;

        private Intersection intersections(Ray ray, Scene scene) {
            double closest = Double.POSITIVE_INFINITY;
            Intersection closestInter = null;

            for (int i = 0; i < scene.things.length; i++) {
                    Intersection inter = scene.things[i].intersect(ray);
                    if (inter != null && inter.dist < closest) {
                        closestInter = inter;
                        closest = inter.dist;
                    }
                }
            return closestInter;
        }

        private double testRay(Ray ray, Scene scene) {
            Intersection isect = intersections(ray, scene);
            if (isect != null) {
                return isect.dist;
            } else {
                return 0;
            }
        }

        private Color traceRay(Ray ray, Scene scene, double depth) {
            Intersection isect = intersections(ray, scene);
            if (isect == null) {
                return Color.background;
            } else {
                return shade(isect, scene, depth);
            }
        }

        private Color shade(Intersection isect, Scene scene, double depth) {
            Vector d = isect.ray.dir;
            Vector pos = Vector.plus(Vector.times(isect.dist, d), isect.ray.start);
            Vector normal = isect.thing.normal(pos);
            Vector reflectDir = Vector.minus(d, Vector.times(2, Vector.times(Vector.dot(normal, d), normal)));
            Color naturalColor = Color.plus(Color.background, getNaturalColor(isect.thing, pos, normal, reflectDir, scene));
            Color reflectedColor = (depth >= this.maxDepth) ? Color.grey : getReflectionColor(isect.thing, pos, normal, reflectDir, scene, depth);
            return Color.plus(naturalColor, reflectedColor);
        }

        private Color getReflectionColor(Thing thing, Vector pos, Vector normal, Vector rd, Scene scene, double depth) {
            return Color.scale(thing.getSurface().reflect(pos), traceRay(new Ray(pos, rd), scene, depth + 1));
        }

        private Color addLight(Color col, Light light, Thing thing, Vector pos, Vector norm, Vector rd, Scene scene) {
            Vector ldis = Vector.minus(light.pos, pos);
            Vector livec = Vector.norm(ldis);
            double neatIsect = testRay(new Ray (pos, livec), scene);
            boolean isInShadow = !(neatIsect == 0) && (neatIsect <= Vector.mag(ldis));
            if (isInShadow) {
                return col;
            } else {
                double illum = Vector.dot(livec, norm);
                Color lcolor = (illum > 0) ? Color.scale(illum, light.color) : Color.defaultColor;
                double specular = Vector.dot(livec, Vector.norm(rd));
                Color scolor = (specular > 0) ? Color.scale(Math.pow(specular, thing.surface.roughness), light.color) : Color.defaultColor;
                return Color.plus(col, Color.plus(Color.times(thing.getSurface().diffuse(pos), lcolor), Color.times(thing.getSurface().specular(pos), scolor)));
            }
        }

        private Color getNaturalColor(Thing thing, Vector pos, Vector norm, Vector rd, Scene scene) {
            Color initialColor = Color.defaultColor;
            Color resultColor = addLight(initialColor, scene.lights[0], thing, pos, norm, rd, scene);

            for (int i = 1; i < scene.lights.length; i++) {
                resultColor = addLight(resultColor, scene.lights[i], thing, pos, norm, rd, scene);
            }

            return resultColor;
/*
                return Arrays.stream(scene.lights)
                        .reduce(
                            Color.defaultColor,
                            (col, light) -> addLight(col.color, light, thing, pos, norm, rd, scene)
                        );
*/
        }


        private double recenterX(double x, int screenWidth) { return (x - (screenWidth / 2.0)) / 2.0 / screenWidth; }
        private double recenterY(double y, int screenHeight) { return - (y - (screenHeight / 2.0)) / 2.0 / screenHeight; }

        private Vector getPoint(double x, double y, Camera camera, int screenWidth, int screenHeight) {
            return Vector.norm(Vector.plus(
                    camera.forward,
                    Vector.plus(
                            Vector.times(recenterX(x, screenWidth), camera.right),
                            Vector.times(recenterY(y, screenHeight), camera.up)
                    ))
            );
        }

        public void render(Scene scene, Graphics graphics, int screenWidth, int screenHeight) {
            for (int y = 0; y < screenHeight; y++) {
                for (int x = 0; x < screenWidth; x++) {
                    Color color = traceRay(new Ray(scene.camera.pos, getPoint(x, y, scene.camera, screenWidth, screenHeight)), scene, 0);
                    java.awt.Color c = Color.toDrawingColor(color);
                    graphics.setColor(c);
                    graphics.fillRect(x, y, x + 1, y + 1);
                }
            }
        }
    }

    public Scene defaultScene() {
        Surfaces surface = new Surfaces();
        Surfaces.checkerboard checkerboard = surface.new checkerboard();
        Surfaces.shiny shiny = surface.new shiny();

        Thing[] things = {
                new Plane(new Vector(0.0, 1.0, 0.0), 0.0, checkerboard),
                new Sphere(new Vector(0.0, 1.0, -0.25), 1.0, shiny),
                new Sphere(new Vector(-1.0, 0.5, 1.5), 0.5, shiny)
        };

        Light[] lights = {
                new Light(new Vector(-2.0, 2.5, 0.0), new Color(0.49, 0.07, 0.07)),
                new Light(new Vector(1.5, 2.5, 1.5), new Color(0.07, 0.07, 0.49)),
                new Light(new Vector(1.5, 2.5, -1.5), new Color(0.07, 0.49, 0.071)),
                new Light(new Vector(0.0, 3.5, 0.0), new Color(0.21, 0.21, 0.35))
        };

        return new Scene(things, lights, new Camera(new Vector(3.0, 2.0, 4.0), new Vector(-1.0, 0.5, 0.0)));
    }

    public void execRender(Graphics graphics, int width, int height) {
        RayTracer rt = new RayTracer();
        rt.render(defaultScene(), graphics, width, height);
    }

}
