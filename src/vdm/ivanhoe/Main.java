package vdm.ivanhoe;

import java.awt.*;
import javax.swing.*;

// import vdm.ivanhoe.JavaRayTracer;

/**
 *  JavaRayTracer
 * (c) 2017, code by Ivan "VanDamM" Kalininskiy
 */
public class Main {
    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
        ImageFrame frame = new ImageFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Graphics graphics = frame.getGraphics();
        // ImageComponent imageComponent = new ImageComponent();

        // imageComponent.paintComponent(graphics);

        int width = 640;
        int height = 640;

        JavaRayTracer rayTracer = new JavaRayTracer();

        rayTracer.execRender(graphics, width, height);

        graphics.dispose();
//            }
//        });
    }
}

class ImageFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 640;
    private static final int DEFAULT_HEIGHT = 640;

    public ImageFrame() {
        setTitle("JavaRayTracer by Ivan \"VanDamM\" Kalininskiy (2017)");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}


class ImageComponent extends JComponent {

    public void paintComponent(Graphics g) {
        int imageWidth = 4;
        int imageHeight = 4;

        for (int i = 0; i * imageWidth <= 640; i++)
            for (int j = 0; j * imageHeight <= 640; j++) {
                    g.setColor(new Color(i, j, 200));
                    g.fillRect(i * imageWidth, j * imageHeight, imageWidth, imageHeight);
                }
    }
}