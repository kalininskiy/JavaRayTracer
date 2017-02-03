package vdm.ivanhoe.raytracer.classes;

import vdm.ivanhoe.raytracer.interfaces.Surface;

/**
 *  Surfaces
 */
public class Surfaces {
    public class ShinySurface implements Surface {
        public Color diffuse(Vector pos) {
            return Color.white();
        }

        public Color specular(Vector pos) {
            return Color.grey();
        }

        public double reflect(Vector pos) {
            return 0.7;
        }

        @Override
        public double getRoughness() {
            return roughness;
        }

        private final double roughness = 250;
    }

    public class CheckerBoardSurface implements Surface {
        public Color diffuse(Vector pos) {
            if ((Math.floor(pos.getZ()) + Math.floor(pos.getX())) % 2 != 0) {
                return Color.black();
            } else {
                return Color.white();
            }
        }

        public Color specular(Vector pos) {
            return Color.black();
        }

        public double reflect(Vector pos) {
            if ((Math.floor(pos.getZ()) + Math.floor(pos.getX())) % 2 != 0) {
                return 0.1;
            } else {
                return 0.8;
            }
        }

        @Override
        public double getRoughness() {
            return roughness;
        }

        private final double roughness = 150;
    }
}
