package physicsCalculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StarTest {

    @Test
    void testStarConstructorSetsValuesCorrectly() {
        Star sun = new Star("Sun", 1.989e30, 0, 0, 0, 0, 0, 0, 6.955e8, Star.StarType.YELLOW_DWARF);

        assertEquals("Sun", sun.getName());
        assertEquals(1.989e30, sun.getMass());
        assertEquals(0, sun.getPositionX());
        assertEquals(0, sun.getPositionY());
        assertEquals(0, sun.getPositionZ());
        assertEquals(0, sun.getVelocityX());
        assertEquals(0, sun.getVelocityY());
        assertEquals(0, sun.getVelocityZ());
        assertEquals(6.955e8, sun.getRadius());
    }

    @Test
    void testStarTypeEnumContainsAllTypes() {
        Star.StarType[] types = Star.StarType.values();

        assertEquals(6, types.length);
        assertEquals(Star.StarType.RED_GIANT, types[0]);
        assertEquals(Star.StarType.BLUE_GIANT, types[1]);
        assertEquals(Star.StarType.BROWN_DWARF, types[2]);
        assertEquals(Star.StarType.WHITE_DWARF, types[3]);
        assertEquals(Star.StarType.NEUTRON_STAR, types[4]);
        assertEquals(Star.StarType.YELLOW_DWARF, types[5]);
    }

    @Test
    void testDifferentStarTypesCanBeCreated() {
        Star redGiant = new Star("Betelgeuse", 1.5e31, 0, 0, 0, 0, 0, 0, 8.87e11, Star.StarType.RED_GIANT);
        Star blueGiant = new Star("Rigel", 3.8e31, 1e15, 0, 0, 0, 0, 0, 5.4e10, Star.StarType.BLUE_GIANT);
        Star brownDwarf = new Star("Teide1", 1.1e29, 2e15, 0, 0, 0, 0, 0, 7e7, Star.StarType.BROWN_DWARF);
        Star whiteDwarf = new Star("Sirius B", 2e30, 3e15, 0, 0, 0, 0, 0, 5.8e6, Star.StarType.WHITE_DWARF);
        Star neutronStar = new Star("PSR B1919+21", 2.8e30, 4e15, 0, 0, 0, 0, 0, 1e4, Star.StarType.NEUTRON_STAR);
        Star yellowDwarf = new Star("Sun", 1.989e30, 0, 0, 0, 0, 0, 0, 6.955e8, Star.StarType.YELLOW_DWARF);

        assertEquals(Star.StarType.RED_GIANT, redGiant.getStarType());
        assertEquals(Star.StarType.BLUE_GIANT, blueGiant.getStarType());
        assertEquals(Star.StarType.BROWN_DWARF, brownDwarf.getStarType());
        assertEquals(Star.StarType.WHITE_DWARF, whiteDwarf.getStarType());
        assertEquals(Star.StarType.NEUTRON_STAR, neutronStar.getStarType());
        assertEquals(Star.StarType.YELLOW_DWARF, yellowDwarf.getStarType());
    }

    @Test
    void testStarInheritsBodyMethods() {
        Star proxima = new Star("Proxima Centauri", 2.4e29, 1e16, 100, 200, 500, 1000, 300, 1.07e8, Star.StarType.RED_GIANT);

        assertEquals("Proxima Centauri", proxima.getName());
        assertEquals(2.4e29, proxima.getMass());
        assertEquals(1e16, proxima.getPositionX());
        assertEquals(100, proxima.getPositionY());
        assertEquals(200, proxima.getPositionZ());

        assertEquals(0, proxima.getNetForceX());
        proxima.updateNetForce(1000, 2000, 3000);
        assertEquals(1000, proxima.getNetForceX());
        assertEquals(2000, proxima.getNetForceY());
        assertEquals(3000, proxima.getNetForceZ());

        proxima.resetForce();
        assertEquals(0, proxima.getNetForceX());
        assertEquals(0, proxima.getNetForceY());
        assertEquals(0, proxima.getNetForceZ());
    }

    @Test
    void testStarPositionAndVelocityUpdate() {
        Star testStar = new Star("Test", 1000, 0, 0, 0, 0, 0, 0, 100, Star.StarType.YELLOW_DWARF);

        testStar.updateNetForce(1000, 0, 0);
        testStar.updatePositionAndVelocityA(1);

        assertEquals(1, testStar.getVelocityX(), 0.001);
        assertEquals(1, testStar.getPositionX(), 0.001);
    }
}
