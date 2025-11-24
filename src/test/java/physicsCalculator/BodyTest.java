package physicsCalculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BodyTest {


    @Test
    void assertThatBodyHasCorrectValuesUponCreation() {
        //basic test to ensure the constructor and object works with storing data appropriately,
        //also useful as i added the Z axis in afterward so good to test that values are in the correct order
        Planet testBody = new Planet("Test", 100, 10, 100, 90, 90, 1000, 80,0, Planet.PlanetType.TERRESTRIAL);
        assertEquals("Test", testBody.getName());
        assertEquals(100, testBody.getMass());
        assertEquals(10, testBody.getPositionX());
        assertEquals(100, testBody.getPositionY());
        assertEquals(90, testBody.getPositionZ());
        assertEquals(90, testBody.getVelocityX());
        assertEquals(1000, testBody.getVelocityY());
        assertEquals(80, testBody.getVelocityZ());
        assertEquals(Planet.PlanetType.TERRESTRIAL, testBody.getPlanetType());
    }

    @Test
    void assertThatResetForceMethodSetsAllForcesToZero() {
        Body testBody = new Planet("Test", 10, 0, 0, 0, 0, 0, 0,0 ,Planet.PlanetType.DWARF);

        testBody.setNetForceX(100);
        testBody.setNetForceY(200);
        testBody.setNetForceZ(300);
        testBody.resetForce();
        assertEquals(0, testBody.getNetForceX());
        assertEquals(0, testBody.getNetForceY());
        assertEquals(0, testBody.getNetForceZ());
    }

    @Test
    void assertThatInitialCoordinatesXYZAreCorrect() {

    }

}
