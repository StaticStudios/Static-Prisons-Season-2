package net.staticstudios.utils.physics;

public class PhysicsUtils {
    /**
     * Formula: (velocityX * time) + (accelerationX * time * time) / 2, (velocityY * time) + (accelerationY * time * time) / 2, (velocityZ * time) + (accelerationZ * time * time) / 2
     * @return The new X, Y, and Z position of the object.
     */
    public static double[] calculatePosition(double[] originalPosition, double velocityX, double accelerationX, double velocityY, double accelerationY, double velocityZ, double accelerationZ, double time) {
        return new double[]{
                originalPosition[0] + velocityX * time + 0.5 * accelerationX * time * time,
                originalPosition[1] + velocityY * time + 0.5 * accelerationY * time * time,
                originalPosition[2] + velocityZ * time + 0.5 * accelerationZ * time * time
        };
    }



}
