package physicsCalculator;


// the abstract keyword makes it so that this is a template for our planet and star classes
public abstract class Body {
//TODO: Write two extended classes for a star and planet to accomplish Advanced OO and Inheritance, add more tests for the methods in Engine, add interfaces and inheritance for advanced OOP
    // this declares the variables we need to use in the N-body formula
    private final String name;
    private final double mass;
    private double positionX, positionY, positionZ;
    private double velocityX, velocityY, velocityZ;
    private double netForceX, netForceY, netForceZ;
    private final double radius;


    /**
     * This is a constructor this uses the variables in the class to create a new object that can be used across our programme
     *  I also added Z in after making the initial program so that is why it is at the end. NOTE: this has been changed after a refactor
     * */
    public Body(String name, double initial_mass, double initial_x, double initial_y, double initial_z, double initial_vx, double initial_vy,  double initial_vz, double radius) {
        this.name = name;
        this.mass = initial_mass;
        this.positionX = initial_x;
        this.positionY = initial_y;
        this.positionZ = initial_z;
        this.velocityX = initial_vx;
        this.velocityY = initial_vy;
        this.velocityZ = initial_vz;
        this.netForceX = 0;
        this.netForceY = 0;
        this.netForceZ = 0;
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getPositionZ() {
        return positionZ;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public double getVelocityZ() {
        return velocityZ;
    }

    public double getNetForceX() {
        return netForceX;
    }

    public double getNetForceY() {
        return netForceY;
    }

    public double getNetForceZ() {
        return netForceZ;
    }

    public void setNetForceX(double netForceX) {
        this.netForceX = netForceX;
    }

    public void setNetForceY(double netForceY) {
        this.netForceY = netForceY;
    }

    public void setNetForceZ(double netForceZ) {
        this.netForceZ = netForceZ;
    }

    public void resetForce() {
        netForceX = 0; //used to set force back to zero on each iteration of the loop so we get accurate calculation because if not then the whole thing blows up :o
        netForceY = 0;
        netForceZ = 0;
    }

    public double getRadius() {
        return radius;
    }

    public void updateNetForce(double forceX, double forceY, double forceZ) {
        netForceX += forceX;
        netForceY += forceY;
        netForceZ += forceZ;
    }

//
//    public double getAcceleration() { // removed this as I thought it added unnecessary complexity, works better just in the method
//        double accelerationX = this.netForceX / this.mass;
//        double accelerationY = this.netForceY / this.mass;
//        return accelerationX,return accelerationY;
//    }


    public void updatePositionAndVelocityA(double timeStep) {

        double accelerationX = netForceX / mass; // acceleration is calculated as force/mass - mass = kg, force = N, acceleration is m/s^2
        double accelerationY = netForceY / mass; // this is newton's second law of motion F = ma or force = mass multiplied by acceleration
        double accelerationZ = this.netForceZ / this.mass;

        this.velocityX += accelerationX * timeStep;
        this.velocityY += accelerationY * timeStep;
        this.velocityZ += accelerationZ * timeStep;

        this.positionX += this.velocityX * timeStep;
        this.positionY += this.velocityY * timeStep;
        this.positionZ += this.velocityZ * timeStep;
    }

    /**
     * @link site i found this on <a href="https://www.geeksforgeeks.org/java/how-to-print-colored-text-in-java-console/">...</a>
     * */
    public String getColorCode() {
        String nameLower = name.toLowerCase();
        if (nameLower.contains("sun")) {
            return "\u001B[33m"; // Yellow
        } else if (nameLower.contains("earth")) {
            return "\u001B[34m"; // Blue
        } else if (nameLower.contains("mars")) {
            return "\u001B[31m"; // Red
        } else if (nameLower.contains("jupiter")) {
            return "\u001B[38;5;208m"; // orange
        } else if (nameLower.contains("saturn")) {
            return "\u001B[38;5;220m"; // gold
        } else if (nameLower.contains("venus")) {
            return "\u001B[38;5;214m"; // orange-yellow
        } else if (nameLower.contains("mercury")) {
            return "\u001B[37m"; // White
        } else if (nameLower.contains("uranus")) {
            return "\u001B[36m"; // Cyan
        } else if (nameLower.contains("neptune")) {
            return "\u001B[34m"; // Blue
        } else {
            return "";
        }
    }

    public String getEmoji() {
        String nameLower = name.toLowerCase();
        if (nameLower.contains("sun")) {
            return "☀️";
        } else if (nameLower.contains("earth")) {
            return "🌍";
        } else if (nameLower.contains("mars")) {
            return "🔴";
        } else if (nameLower.contains("jupiter")) {
            return "🟠";
        } else if (nameLower.contains("saturn")) {
            return "🪐";
        } else if (nameLower.contains("venus")) {
            return "🌕";
        } else if (nameLower.contains("mercury")) {
            return "⚪️";
        } else if (nameLower.contains("uranus")) {
            return "🔵";
        } else if (nameLower.contains("neptune")) {
            return "🔵";
        } else if (nameLower.contains("moon")) {
            return "🌙";
        } else {
            return "";
        }
    }
}