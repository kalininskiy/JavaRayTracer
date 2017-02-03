package vdm.ivanhoe.raytracer.classes;

/**
 *  Color
 */
public class Color {
    private double r, g, b;

    private static Color white = new Color(1.0, 1.0, 1.0);
    private static Color grey = new Color(0.5, 0.5, 0.5);
    private static Color black = new Color(0.0, 0.0, 0.0);
    private static Color background = black;
    private static Color defaultColor = black;

    public Color(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static Color scale(double k, Color v) {
        return new Color(k * v.r, k * v.g, k * v.b);
    }

    public static Color plus(Color v1, Color v2) {
        return new Color(v1.r + v2.r, v1.g + v2.g, v1.b + v2.b);
    }

    public static Color times(Color v1, Color v2) {
        return new Color(v1.r * v2.r, v1.g * v2.g, v1.b * v2.b);
    }

    private static double legalize(double d) {
        return (d > 1) ? 1 : d;
    }

    public static java.awt.Color toDrawingColor(Color c) {
        return new java.awt.Color(
                (int) Math.floor(legalize(c.r) * 255),
                (int) Math.floor(legalize(c.g) * 255),
                (int) Math.floor(legalize(c.b) * 255)
        );
    }

    public double r() {
        return r;
    }

    public double g() {
        return g;
    }

    public double b() {
        return b;
    }

    public static Color white() {
        return white;
    }

    public static Color grey() {
        return grey;
    }

    public static Color black() {
        return black;
    }

    public static Color background() {
        return background;
    }

    public static Color defaultColor() {
        return defaultColor;
    }
}
