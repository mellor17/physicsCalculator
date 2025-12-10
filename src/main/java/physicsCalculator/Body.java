package physicsCalculator;


// the abstract keyword makes it so that this is a template for our planet and star classes
public abstract class Body {
    // this declares the variables(fields when in a class) we need to use in the N-body formula
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

    // these are getters and setter for our fields, this is how OOP is supposed to work so no other methods can alter our variables accidentally
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

    public double getRadius() {
        return radius;
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


    /**
     *
     * The reason why these methods are in the body class is because i feel like it is easier to access them in the engine and body because then we don't have to pass parms
     * each time the method is called
     * This is a helper method which use ANSI colour code to recolour bodies based on the type of planet
     * Found this information from this site:
     * @link  <a href="https://www.geeksforgeeks.org/java/how-to-print-colored-text-in-java-console/">...</a>
     *
     * */
    public String getColorCode() {
        String nameLower = name.toLowerCase();
        String[] colourCodes = {"\u001B[33m", "\u001B[34m", "\u001B[31m", "\u001B[38;5;208m", "\u001B[38;5;220m", "\u001B[38;5;214m", "\u001B[37m", "\u001B[36m", "\u001B[34m"};

        if (nameLower.contains("sun")) {
            return colourCodes[0]; // yellow
        } else if (nameLower.contains("earth")) {
            return colourCodes[1]; // blue
        } else if (nameLower.contains("mars")) {
            return colourCodes[2]; // red
        } else if (nameLower.contains("jupiter")) {
            return colourCodes[3]; // orange
        } else if (nameLower.contains("saturn")) {
            return colourCodes[4]; // gold
        } else if (nameLower.contains("venus")) {
            return colourCodes[5]; // orange-yellow
        } else if (nameLower.contains("mercury") || nameLower.contains("moon")) {
            return colourCodes[6]; // white
        } else if (nameLower.contains("uranus")) {
            return colourCodes[7]; // cyan
        } else if (nameLower.contains("neptune")) {
            return colourCodes[8]; // blue
        } else {
            return colourCodes[(int) Math.abs(Math.random() * colourCodes.length)]; // just returns any random colour if name not found
        }
    }

    //NOTE: IF YOU ARE RUNNING ON A WINDOWS COMPUTER THEN THESE MIGHT NOT DISPLAY AS CMD PROMPT CANNOT DISPLAY EMOJIS!!!!!!
    // if not then you are ok :)
    public String getEmoji() {
        String nameLower = name.toLowerCase();
        String[] emojiArray = {"☀️", "🌍","🔴", "🟠", "🪐", "🌕", "⚪️", "🔵", "🌙"};
        if (nameLower.contains("sun")) {
            return emojiArray[0];
        } else if (nameLower.contains("earth")) {
           return emojiArray[1];
        } else if (nameLower.contains("mars")) {
            return emojiArray[2];
        } else if (nameLower.contains("jupiter")) {
            return emojiArray[3];
        } else if (nameLower.contains("saturn")) {
            return emojiArray[4];
        } else if (nameLower.contains("venus")) {
            return emojiArray[5];
        } else if (nameLower.contains("mercury")) {
            return emojiArray[6];
        } else if (nameLower.contains("uranus")) {
            return emojiArray[7];
        } else if (nameLower.contains("neptune")) {
            return emojiArray[7];
        } else if (nameLower.contains("moon")) {
            return emojiArray[8];
        } else {
            return "";
        }
    }
}