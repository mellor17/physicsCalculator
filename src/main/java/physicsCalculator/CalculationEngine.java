package physicsCalculator;

import java.util.ArrayList;

public class CalculationEngine {
    // the letter e is used to denote the power of 10, so
    // mass of sun would be 1.989 x 10^30kg
    private static final double gravitationalConstant = 6.674e-11;


    // static just means that the objects or items within that scope are accessible without needing to instantiate a new instance
    // of the class that holds it
    public static void calculateNBodyProblem(ArrayList<Body> celestialBodies, double timeStep, double totalDuration, boolean isTesting) {
        for (double t = 0; t < totalDuration; t += timeStep) {         // this is the time loop for each iteration of the simulation
            for (Body body : celestialBodies) {
                body.resetForce();

            }

            for (int i = 0; i < celestialBodies.size(); i++) {
                for (int j = i + 1; j < celestialBodies.size(); j++) { // adding one ensures that when running the simulation we don't calculate the same pair twice, so e.g A = B

                    Body bodyA = celestialBodies.get(i);
                    Body bodyB = celestialBodies.get(j);


                    calculateForcesAndApplyValues(bodyA, bodyB);

                }
            }

            for (Body body : celestialBodies) {
                body.updatePositionAndVelocityA(timeStep);
            }

            if (!isTesting) {
                int printFrequency = 100;
                if (t == 0 || (t / timeStep) % printFrequency == 0) {
                    System.out.printf("\u001B[35m" + "--- Time: %.0f s --- \n", t);
                    for (Body currentBody : celestialBodies) {
                        System.out.printf("  %s Position:\n", currentBody.name);
                        System.out.printf("    X: %.4e\n", currentBody.positionX);
                        System.out.printf("    Y: %.4e\n", currentBody.positionY);
                        System.out.printf("    Z: %.4e\n", currentBody.positionZ);

                    }
                    System.out.println("---------------------");
                }

                try {
                    Thread.sleep(500); // this is used to slow down the output of the application in the console so the user can see what the output is
                } catch (InterruptedException exception) {
                    exception.printStackTrace(); // this is required by the sleep method if you look at the method inforamtion
                }
            }
        }
    }


    public static double calculateTotalEnergy(ArrayList<Body> celestialBodies) {
        double totalPotentialEnergy = calculateTotalPotentialEnergy(celestialBodies);

        double totalKineticEnergy = calculateTotalKineticEnergy(celestialBodies);

        return totalKineticEnergy + totalPotentialEnergy;

    }

    public static double calculateTotalKineticEnergy(ArrayList<Body> bodies) {

        double totalKineticEnergy = 0;

        for (Body body : bodies) {

            double bodySpeedSquared = (Math.pow(body.velocityX, 2)) + (Math.pow(body.velocityY, 2)) + (Math.pow(body.velocityZ, 2));

            double bodyKineticEnergy = 0.5 * body.mass * bodySpeedSquared;
            totalKineticEnergy += bodyKineticEnergy;
        }

        return totalKineticEnergy;
    }

    public static double calculateTotalPotentialEnergy(ArrayList<Body> celestialBodies) {
        double totalPotentialEnergy = 0;

        for (int i = 0; i < celestialBodies.size(); i++) {
            for (int j = 0; j < celestialBodies.size(); j++) {
                Body bodyA = celestialBodies.get(i);
                Body bodyB = celestialBodies.get(j);

                double totalDistance = getTotalDistance(bodyB, bodyA);

                if (totalDistance == 0) {
                    continue; // skip if they are in the same spot so we can't have two bodies in the same position which can break things
                } // also it is not physically possible
                double pairedPotentialEnergy = -gravitationalConstant * (bodyA.mass * bodyB.mass / totalDistance);
                totalPotentialEnergy += pairedPotentialEnergy;

            }
        }
        return totalPotentialEnergy;

    }

    private static double getTotalDistance(Body bodyB, Body bodyA) {
        double distanceX = bodyB.positionX - bodyA.positionX;// bodyb must be first because if you take away a from b we will get a negative result, it would mean that the force acting on b is pushing it away so not gravity
        double distanceZ = bodyB.positionZ - bodyA.positionZ;
        double distanceY = bodyB.positionY - bodyA.positionY;

        // r in our formula total distace
        return Math.sqrt((distanceX * distanceX) + (distanceY * distanceY) + (distanceZ * distanceZ));
    }


    public static void calculateForcesAndApplyValues(Body bodyA, Body bodyB) {

        // also i don't think i can use my method here as it needs these variables further down
        double distanceX = bodyB.positionX - bodyA.positionX;
        double distanceY = bodyB.positionY - bodyA.positionY;
        double distanceZ = bodyB.positionZ - bodyA.positionZ;
        double totalDistance = getTotalDistance(bodyB, bodyA);

        if (totalDistance == 0)
            return; // this stops us dividing by zero which can be catastrophic for a program i think}
        double magnitudeOfForce = gravitationalConstant * (bodyA.mass * bodyB.mass) / Math.pow(totalDistance, 2);


        // This calculates the force for Body A, to find the force for bodyB is just the opposite, so negative force X, Y & Z
        double forceX = magnitudeOfForce * (distanceX / totalDistance);
        double forceY = magnitudeOfForce * (distanceY / totalDistance);
        double forceZ = magnitudeOfForce * (distanceZ / totalDistance);

        bodyA.updateNetForce(forceX, forceY, forceZ);
        bodyB.updateNetForce(-forceX, -forceY, -forceZ); // this saves code and time because we now don't have to calculate the force for the other bod
        // we just know that the force for body B will directly opposite to A


    }


    public static double calculateGravitionalForce(Body bodyA, Body bodyB) {
        double totalDistance = getTotalDistance(bodyB, bodyA);
        return gravitationalConstant  * (bodyA.mass * bodyB.mass) / Math.pow(2, totalDistance);
    }

    public static double calculateCentripetalForce(Body bodyA, Body bodyB) {
        double totalDistance = getTotalDistance(bodyB, bodyA);
        double centripetalForce = 0;
        return centripetalForce;
    }


}
