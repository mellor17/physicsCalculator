package physicsCalculator;

import java.util.ArrayList;

import static physicsCalculator.InputUtility.*;

public class CalculationEngine {
    // the letter e is used to denote the power of 10, so e.g.
    // mass of sun would be 1.989 x 10^30kg
    public static final double gravitationalConstant = 6.674e-11;
    public static boolean isFinished = false;
    public static boolean watchSimulation = false;

    // static just means that the objects or items within that scope are accessible without needing to instantiate a new instance
    // of the class that holds it
    public static void calculateNBodyProblem(ArrayList<Body> celestialBodies, double timeStep, double totalDuration, boolean isTesting) {
        for (double currentDuration = 0; currentDuration <= totalDuration; currentDuration += timeStep) {         // this is the time loop for each iteration of the simulation
            if (isFinished) break;
            for (Body body : celestialBodies) { // currentDuration just means the current time in the calculator, so at the start it will be
                body.resetForce();
            }


            //This loop works by iterating over i first then through j so we have a calculation for each body with the arraylist
            for (int i = 0; i < celestialBodies.size(); i++) {
                for (int j = i + 1; j < celestialBodies.size(); j++) { // adding one ensures that when running the simulation we don't calculate the same pair twice, so e.g A = B

                    Body bodyA = celestialBodies.get(i);
                    Body bodyB = celestialBodies.get(j);

                    if (checkForCollisionDetection(bodyA, bodyB)) {
                        isFinished = true;
                    }
                    calculateForcesAndApplyValues(bodyA, bodyB);

                }
            }

            for (Body body : celestialBodies) {
                body.updatePositionAndVelocityA(timeStep);
            }
                if (!isTesting) {
                    int printFrequency = 100;
                    if (currentDuration == 0 || (currentDuration / timeStep) % printFrequency == 0) {


                        System.out.printf("\u001B[35m" + "--- Time: %s --- \n", CalculationBody.timeConversionMethod(currentDuration));
                        for (Body currentBody : celestialBodies) {
                            String color = currentBody.getColorCode();
                            String emoji = currentBody.getEmoji();
                            String reset = "\u001B[0m";
                            System.out.printf("%s  %s %s Position:\n", color, emoji, currentBody.getName());
                            System.out.printf("    X: %.4e\n", currentBody.getPositionX());
                            System.out.printf("    Y: %.4e\n", currentBody.getPositionY());
                            System.out.printf("    Z: %.4e\n%s", currentBody.getPositionZ(), reset);

                        }
                        System.out.println("---------------------");
                    }

                    if (watchSimulation) {
                        try {
                            Thread.sleep(5); // this is used to slow down the output of the application in the console so the user can see what the output is otherwise it finishes instantly
                        } catch (InterruptedException exception) {
                            exception.printStackTrace(); // this is required by the sleep method if you look at the method inforamtion
                        }
                    }

                }

        }
        if (!isTesting) {
            String finishedResponse = inputCheckerString("Are you finished simulating? (Y/N)");
            if (finishedResponse.toLowerCase().contains("y")) {
                System.out.println(CalculationBody.randomMessageGenerator());
                isFinished = true;
            }
        }

    }

    // Takes both results from the energy methods and adds them together, used to test conservation of energy
    public static double calculateTotalEnergy(ArrayList<Body> celestialBodies) {
        return calculateTotalKineticEnergy(celestialBodies) + calculateTotalPotentialEnergy(celestialBodies);
    }


    /**
     * The formula for this method is:
     * KE = 1/2mv^2
     * In English this means:
     * Kinetic Energy = 0.5 or half multiplied by mass, by velocity squared
     * This method accomplishes this by looping through each body in the Body ArrayList.
     * Then it calculated the speed of each body by adding the velocity squared of X,Y,Z and then getting a new value of the kinetic energy of the body it is iterating over
     * by multiplying 0.5, the mass of the body and then the velocity squared or speed
     * Finally it is added to the total kinetic energy to return the value for the calculate total energy method
     */
    public static double calculateTotalKineticEnergy(ArrayList<Body> bodies) {

        double totalKineticEnergy = 0;

        for (Body body : bodies) {

            double bodySpeedSquared = (Math.pow(body.getVelocityX(), 2)) + (Math.pow(body.getVelocityY(), 2)) + (Math.pow(body.getVelocityZ(), 2));

            double bodyKineticEnergy = 0.5 * body.getMass() * bodySpeedSquared;
            totalKineticEnergy += bodyKineticEnergy;
        }

        return totalKineticEnergy;
    }


    /**
     * Potential Energy has the formula:
     * U = -G * (m₁ * m₂) / r
     * Potential Energy = negative gravitational constant multiplied by (mass 1 * mass 2) divided by total distance
     */
    public static double calculateTotalPotentialEnergy(ArrayList<Body> celestialBodies) {
        double totalPotentialEnergy = 0;

        for (int i = 0; i < celestialBodies.size(); i++) {
            for (int j = i + 1; j < celestialBodies.size(); j++) { // we use the i + 1 to ensure that we avoid double counting pairs so, A-B, B-A etc would double the amount of total potential energy which is wrong!!!
                Body bodyA = celestialBodies.get(i);
                Body bodyB = celestialBodies.get(j);


                double totalDistance = getTotalDistance(bodyB, bodyA);

                if (totalDistance == 0) {
                    continue; // skip if they are in the same spot so we can't have two bodies in the same position which can break things
                } // also it is not physically possible
                double pairedPotentialEnergy = -gravitationalConstant * (bodyA.getMass() * bodyB.getMass() / totalDistance);
                totalPotentialEnergy += pairedPotentialEnergy;

            }
        }
        return totalPotentialEnergy;
    }

    /**
     * This method takes values from each body parameter and then returns them through the total distance formula:
     * r = √x^2 * y^2 * z^2
     * Total distance =  square root of x^2 multiplied by y^2 multiplied by z^2
     *
     */
    public static double getTotalDistance(Body bodyB, Body bodyA) {
        // bodyB must be first because if you take away a from b we will get a negative result, it would mean that the force acting on b is pushing it away so that is not gravity but repulsion
        double distanceX = bodyB.getPositionX() - bodyA.getPositionX();
        double distanceZ = bodyB.getPositionZ() - bodyA.getPositionZ();
        double distanceY = bodyB.getPositionY() - bodyA.getPositionY();

        // r in our formula total distance
        return Math.sqrt((distanceX * distanceX) + (distanceY * distanceY) + (distanceZ * distanceZ));
    }


    public static void calculateForcesAndApplyValues(Body bodyA, Body bodyB) {

        // also i don't think i can use my method here as it needs these variables further down
        double distanceX = bodyB.getPositionX() - bodyA.getPositionX();
        double distanceY = bodyB.getPositionY() - bodyA.getPositionY();
        double distanceZ = bodyB.getPositionZ() - bodyA.getPositionZ();
        double totalDistance = getTotalDistance(bodyB, bodyA);

        if (totalDistance == 0)
            return; // this stops us dividing by zero which can be catastrophic for a program i think

        double magnitudeOfForce = gravitationalConstant * (bodyA.getMass() * bodyB.getMass()) / Math.pow(totalDistance, 2);


        // This calculates the force for Body A, to find the force for bodyB is just the opposite, so negative force X, Y & Z
        double forceX = magnitudeOfForce * (distanceX / totalDistance);
        double forceY = magnitudeOfForce * (distanceY / totalDistance);
        double forceZ = magnitudeOfForce * (distanceZ / totalDistance);

        bodyA.updateNetForce(forceX, forceY, forceZ);
        bodyB.updateNetForce(-forceX, -forceY, -forceZ); // this saves code and time because we now don't have to calculate the force for the other bod
        // we just know that the force for body B will directly opposite to A

    }


    public static double calculateGravitationalForce(Body bodyA, Body bodyB) {
        double totalDistance = getTotalDistance(bodyB, bodyA);
        return gravitationalConstant * (bodyA.getMass() * bodyB.getMass()) / Math.pow(totalDistance, 2);
    }


    /***
     * Formula for centripetal force used to test whether we have a stable orbit:
     * Fe = m * v^2 / r
     * Centripetal Force = mass of rotating body * orbital velocity squared / total distance  between both bodies
     */
    public static double calculateCentripetalForce(Body bodyA, Body bodyB) {
        double totalDistance = getTotalDistance(bodyB, bodyA);
        double massOfBodyB = bodyB.getMass();
        double orbitalVelocity = Math.sqrt(Math.pow(bodyB.getVelocityX(), 2)
                + Math.pow(bodyB.getVelocityY(), 2)
                + Math.pow(bodyB.getVelocityZ(), 2));
        return (massOfBodyB * Math.pow(orbitalVelocity, 2)) / totalDistance;
    }

//    public static double calculateOrbitalVelocity(Body mainBody, double distance) {
//        return Math.sqrt(gravitationalConstant * mainBody.getMass() / distance);
//
//    }

    public static boolean checkForCollisionDetection(Body bodyA, Body bodyB) {
        double totalDistance = CalculationEngine.getTotalDistance(bodyA, bodyB);
        if (totalDistance <= bodyA.getRadius() + bodyB.getRadius()) {
            System.out.printf("\u001B[31m💥 COLLISION DETECTED! 💥\n%s %s and %s %s have collided! Simulation ending!\u001B[0m\n",
                    bodyA.getEmoji(), bodyA.getName(), bodyB.getEmoji(), bodyB.getName());
            return true;
        } else {
            return false;
        }

    }



}
