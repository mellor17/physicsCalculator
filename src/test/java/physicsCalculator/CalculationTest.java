package physicsCalculator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static physicsCalculator.CalculationBody.*; // uses the static variables in calculation body for our tests

public class CalculationTest {


    @Test
    void assertThatTotalEnergyCalculationsReturnCorrectValues() {

    }

    @Test
    void assertThatKineticEnergyCalculationsReturnCorrectValues() {

    }

    @Test
    void assertThatPotentialEnergyCalculationsReturnCorrectValues() {

    }


    /**
     * Due to the nature of the n-body problem it is difficult to get specifically accurate figures when testing the system,
     * but we can approximate using smaller figures and test different concepts like the concept of conservation of energy.
     * As we are working within a closed/isolated system, the law of conservation states that the amount of energy in it will be constant. (hopefully ¯\_(ツ)_/¯)
     * We still can test however using approximate figures and to a number of decimal places for tolerance
     */
    @Test
    void testConservationOfEnergyWithTwoBodies() {
        ArrayList<Body> testBodies = new ArrayList<>();
        Body sun = new Star("Sun",massOfSun, 0, 0, 0, 0, 0, 0    );
        Body earth = new Planet("Earth", massOfEarth, 1.496e11, 0, 0, 0, 29780, 0);
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
        Body sun = new Star("Sun",massOfSun, 0, 0, 0, 0, 0, 0    );
        Body earth = new Planet("Earth", massOfEarth, 1.496e11, 0, 0, 0, 29780, 0);
        Body mars = new Planet("Mars" ,massOfMars, 2.279e11, 0, 0, 0, 24070, 0);
        // initial velocity y for earth is the average speed is 29,780 is m/s ^

        testBodies.add(sun);
        testBodies.add(earth);
        testBodies.add(mars);

        double initialEnergy = CalculationEngine.calculateTotalEnergy(testBodies);

        CalculationEngine.calculateNBodyProblem(testBodies, 60, CalculationBody.yearInSeconds, true);

        double tolerance = 1e31; // margin for error looks huge but in relation to the simulation it is actually quite small only tiny percentage
        double finalEnergy = CalculationEngine.calculateTotalEnergy(testBodies);
        System.out.println("Initial Energy: " + initialEnergy + "\nTotal Energy: " + finalEnergy + "\n");
        // result will be negative because we are only working in 2d for now and that means eventually the body will be in a negative position on a coordinate plane
        assertEquals(initialEnergy, finalEnergy, tolerance);
    }


    @Test
    void testBasicTwoBodySystemForStableCircularOrbit() {
        ArrayList<Body> testBodies = new ArrayList<>();
        Body body1 = new Star("test1", massOfSun, 0, 0, 0, 0, 0, 0);
        Body body2 = new Planet("test2", massOfEarth, 1.496e11, 0, 0, 0, 29780, 0);
        testBodies.add(body1);
        testBodies.add(body2);

        CalculationEngine.calculateNBodyProblem(testBodies, 60, CalculationBody.sixMonthsInSeconds, true);


    }

    @Test
    void testForStableCircularOrbit() {
        ArrayList<Body> testBodies = new ArrayList<>();
        Body body1 = new Planet("test1", massOfSun, 0, 0, 0, 0, 0, 0);
        Body body2 = new Planet("test2", massOfEarth, 1.496e11, 0, 0, 0, 29780, 0);
        testBodies.add(body1);
        testBodies.add(body2);


    }





}
