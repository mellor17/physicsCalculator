package physicsCalculator;

public abstract class Body {
//TODO: Write two extended classes for a star and planet to accomplish Advanced OO and Inheritance, add more tests for the methods in Engine, add interfaces and inheritance for advanced OOP
    // this declares the variables we need to use in the N-body formula
    private final String name;
    private final double mass;
    private double positionX, positionY, positionZ;
    private double velocityX, velocityY, velocityZ;
    private double netForceX, netForceY, netForceZ;
    /**
     * This is a constructor this uses the variables in the class to create a new object that can be used across our programme
     *  I also added Z in after making the initial program so that is why it is at the end. NOTE: this has been changed after a refactor
     * */
    public Body(String name, double initial_mass, double initial_x, double initial_y, double initial_z, double initial_vx, double initial_vy,  double initial_vz) {
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
}