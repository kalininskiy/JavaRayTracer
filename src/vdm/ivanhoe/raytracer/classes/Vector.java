package vdm.ivanhoe.raytracer.classes;

/**
 *  Vector
 */
public class Vector {
    private double x, y, z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public static Vector times(double k, Vector v) {
        return new Vector(k * v.x, k * v.y, k * v.z);
    }

    public static Vector minus(Vector v1, Vector v2) {
        return new Vector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }

    public static Vector plus(Vector v1, Vector v2) {
        return new Vector(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }

    public static double dot(Vector v1, Vector v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    public static double getLength(Vector v) {
        return Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
    }

    public static Vector normal(Vector v) {
        double length = getLength(v);
        double div = (length == 0) ? Double.POSITIVE_INFINITY : (1.0 / length);
        return times(div, v);
    }

    static Vector cross(Vector v1, Vector v2) {
        return new Vector(v1.y * v2.z - v1.z * v2.y,
                v1.z * v2.x - v1.x * v2.z,
                v1.x * v2.y - v1.y * v2.x);
    }
}
