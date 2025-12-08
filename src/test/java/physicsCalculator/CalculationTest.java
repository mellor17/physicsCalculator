package physicsCalculator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static physicsCalculator.CalculationBody.*; // uses the static variables in calculation body for our tests

/**
 * Due to the nature of the n-body problem it is difficult to get specifically accurate figures when testing the system,
 * but we can approximate using smaller figures and test different concepts like the concept of conservation of energy.
 * As we are working within a closed/isolated system, the law of conservation states that the amount of energy in it will be constant. (hopefully ¯\_(ツ)_/¯)
 * We still can test however using approximate figures and to a number of decimal places for tolerance
 */
public class CalculationTest {

    @Test
    void assertThatTotalEnergyCalculationsReturnCorrectValues() {
        ArrayList<Body> testBodies = new ArrayList<>();
        Body sun = new Star("Sun", massOfSun, 0, 0, 0, 0, 0, 0, radiusOfSun, Star.StarType.YELLOW_DWARF);
        Body earth = new Planet("Earth", massOfEarth, 1.496e11, 0, 0, 0, 29780, 0, radiusOfEarth, Planet.PlanetType.TERRESTRIAL);

        testBodies.add(sun);
        testBodies.add(earth);

        double totalEnergy = CalculationEngine.calculateTotalEnergy(testBodies);
        double kineticEnergy = CalculationEngine.calculateTotalKineticEnergy(testBodies);
        double potentialEnergy = CalculationEngine.calculateTotalPotentialEnergy(testBodies);

        // Total energy should be equal to kinetic + potential energy
        assertEquals(kineticEnergy + potentialEnergy, totalEnergy, 1e10);

        // For a stable orbit, total energy should be negative
        assertTrue(totalEnergy < 0);
    }

    @Test
    void assertThatKineticEnergyCalculationsReturnCorrectValues() {
        ArrayList<Body> testBodies = new ArrayList<>();
        // KE = 0.5 * m * v^2
        double mass = 1000.0;
        double velocityX = 100.0;
        double velocityY = 200.0;
        double velocityZ = 0.0;

        Body testBody = new Planet("TestBody", mass, 0, 0, 0, velocityX, velocityY, velocityZ, 10, Planet.PlanetType.TERRESTRIAL);
        testBodies.add(testBody);
//        System.out.println( 0.5 * 1000 * (Math.pow(100, 2) + Math.pow(200,2) + Math.pow(0,2))); this returns 2.5e7, which is right because 25 mill has 7 zeros
        // Expected KE = 0.5 * 1000 * (100^2 + 200^2 + 0^2) = 0.5 * 1000 * 50000 = 25,000,000
        double expectedKineticEnergy = 0.5 * mass * (Math.pow(velocityX, 2) + Math.pow(velocityY, 2) + Math.pow(velocityZ, 2));
        double actualKineticEnergy = CalculationEngine.calculateTotalKineticEnergy(testBodies);

        assertEquals(expectedKineticEnergy, actualKineticEnergy, 0.01);

        // Kinetic energy should always be positive or zero
        assertTrue(actualKineticEnergy >= 0);
    }


    @Test
    void assertThatPotentialEnergyCalculationsReturnCorrectValues() {
        ArrayList<Body> testBodies = new ArrayList<>();
        // PE = -G * (m1 * m2) / r
        // U is also sometimes used to denote potential energy
        // potential energy = negative gravitational constant multiplied by (mass1 times mass 2) then divided by total distance
        double mass1 = 1.0e20;
        double mass2 = 1.0e20;
        double distance = 1.0e10;

        Body body1 = new Planet("Body1", mass1, 0, 0, 0, 0, 0, 0, 10, Planet.PlanetType.TERRESTRIAL);
        Body body2 = new Planet("Body2", mass2, distance, 0, 0, 0, 0, 0, 10, Planet.PlanetType.TERRESTRIAL);

        testBodies.add(body1);
        testBodies.add(body2);

        // Expected PE = -6.674e-11 * (1e20 * 1e20) / 1e10 = -6.674e19
        double expectedPotentialEnergy = -6.674e-11 * (mass1 * mass2) / distance;
        double actualPotentialEnergy = CalculationEngine.calculateTotalPotentialEnergy(testBodies);

        assertEquals(expectedPotentialEnergy, actualPotentialEnergy, 1e10);

        // Gravitational potential energy should always be negative or zero as for one we have G which is negative in the GPE equation and two potential e
        System.out.println(actualPotentialEnergy);
        System.out.println(expectedPotentialEnergy);
        assertTrue(actualPotentialEnergy <= 0);
    }


    /**
     * This test makes sure the system follows the law of energy conservation which states:
     * The total energy of an isolated system remains constant or conserved over time.
     * It uses the calculateTotalEnergy method which then calculates total kinetic and potential energy
     * */
    @Test
    void testConservationOfEnergyWithTwoBodies() {
        ArrayList<Body> testBodies = new ArrayList<>();
        Body sun = new Star("Sun",massOfSun, 0, 0, 0, 0, 0, 0, radiusOfSun, Star.StarType.YELLOW_DWARF    );
        Body earth = new Planet("Earth", massOfEarth, 1.496e11, 0, 0, 0, 29780, 0,radiusOfEarth , Planet.PlanetType.TERRESTRIAL);
        // initial velocity y for earth is the average speed is 29,780 is m/s ^

        testBodies.add(sun);
        testBodies.add(earth);

        double initialEnergy = CalculationEngine.calculateTotalEnergy(testBodies);

        CalculationEngine.calculateNBodyProblem(testBodies, 60, CalculationBody.yearInSeconds, true);

        double tolerance = 1e26; // margin for error looks huge but in relation to the simulation it is actually quite small only tiny percentage
        double finalEnergy = CalculationEngine.calculateTotalEnergy(testBodies);
        System.out.println("Initial Energy: " + initialEnergy + "\nTotal Energy: " + finalEnergy + "\n"); // result will be negative because we are only working in 2d for now and he
        assertEquals(initialEnergy, finalEnergy, tolerance);
    }

    @Test
    void testConservationOfEnergyWithThreeBodies() {
        ArrayList<Body> testBodies = new ArrayList<>();
        Body sun = new Star("Sun",massOfSun, 0, 0, 0, 0, 0, 0,radiusOfSun, Star.StarType.YELLOW_DWARF  );
        // initial velocity y for earth is the average speed is 29,780 is m/s
        Body earth = new Planet("Earth", massOfEarth, 1.496e11, 0, 0, 0, 29780, 0,radiusOfEarth, Planet.PlanetType.TERRESTRIAL);
        // initial velocity y for mars is the average speed is 24,070 is m/s a little slower than the earth due to its smaller size and it being further away from the sun hence the slower orbit
        Body mars = new Planet("Mars" ,massOfMars, 2.279e11, 0, 0, 0, 24070, 0, radiusOfMars, Planet.PlanetType.TERRESTRIAL);


        testBodies.add(sun);
        testBodies.add(earth);
        testBodies.add(mars);

        double initialEnergy = CalculationEngine.calculateTotalEnergy(testBodies);

        CalculationEngine.calculateNBodyProblem(testBodies, 60, CalculationBody.yearInSeconds, true);

        double tolerance = 1e31; // margin for error looks huge but in relation to the simulation and the figures we are working it is actually quite small only tiny percentage
        double finalEnergy = CalculationEngine.calculateTotalEnergy(testBodies);
        System.out.println("Initial Energy: " + initialEnergy + "\nTotal Energy: " + finalEnergy + "\n");
        // result will be negative because we are only working in 2d for now and that means eventually the body will be in a negative position on a coordinate plane
        assertEquals(initialEnergy, finalEnergy, tolerance);
    }

    @Test
    void testBasicTwoBodySystemForStableCircularOrbit() {
        Body sun = new Star("sun", massOfSun, 0, 0, 0, 0, 0, 0, massOfSun, Star.StarType.YELLOW_DWARF);
        Body earth = new Planet("earth", massOfEarth, 1.496e11, 0, 0, 0, 29780, 0,massOfEarth , Planet.PlanetType.TERRESTRIAL);
        double tolerance = 1e22;

        double gravitationalForce = CalculationEngine.calculateGravitationalForce(earth, sun);
        double centripetalForce = CalculationEngine.calculateCentripetalForce(sun, earth);
        System.out.println(centripetalForce);
        System.out.println(gravitationalForce);


        double difference = Math.abs(gravitationalForce - centripetalForce);
        System.out.println(difference);
        boolean isOrbitCircular = false;
        if (difference < tolerance) {
            isOrbitCircular = true;
        }

        assertTrue(isOrbitCircular);


    }

    @Test
    void testCollisionDetectionBetweenTwoBodies() {
        Body target = new Star("Target", 1.989e30, 0, 0, 0, 0, 0, 0,7e8, Star.StarType.NEUTRON_STAR);

        Body bullet = new Planet("Bullet", 1000, 7.00001e8, 0, 0, -1000, 0, 0, 10, Planet.PlanetType.GAS_GIANT);

        ArrayList<Body> bodies = new ArrayList<>();
        bodies.add(target);
        bodies.add(bullet);

        CalculationEngine.calculateNBodyProblem(bodies, 1, 1, true);
        double totalDistance = CalculationEngine.getTotalDistance(bullet, target);
        assertTrue(totalDistance <= (target.getRadius() + bullet.getRadius()));
    }



}
