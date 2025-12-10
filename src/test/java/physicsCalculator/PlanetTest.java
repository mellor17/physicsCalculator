package physicsCalculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlanetTest {

    @Test
    void testPlanetConstructorSetsValuesCorrectly() {
        Planet earth = new Planet("Earth", 5.972e24, 1.496e11, 0, 0, 0, 29780, 0, 6.371e6, Planet.PlanetType.TERRESTRIAL);

        assertEquals("Earth", earth.getName());
        assertEquals(5.972e24, earth.getMass());
        assertEquals(1.496e11, earth.getPositionX());
        assertEquals(0, earth.getPositionY());
        assertEquals(0, earth.getPositionZ());
        assertEquals(0, earth.getVelocityX());
        assertEquals(29780, earth.getVelocityY());
        assertEquals(0, earth.getVelocityZ());
        assertEquals(6.371e6, earth.getRadius());
        assertEquals(Planet.PlanetType.TERRESTRIAL, earth.getPlanetType());
    }

    @Test
    void testPlanetTypeEnumContainsAllTypes() {
        Planet.PlanetType[] types = Planet.PlanetType.values();

        assertEquals(5, types.length);
        assertEquals(Planet.PlanetType.GAS_GIANT, types[0]);
        assertEquals(Planet.PlanetType.TERRESTRIAL, types[1]);
        assertEquals(Planet.PlanetType.DWARF, types[2]);
        assertEquals(Planet.PlanetType.ICE_GIANT, types[3]);
        assertEquals(Planet.PlanetType.EXOPLANET, types[4]);
    }

    @Test
    void testDifferentPlanetTypesCanBeCreated() {
        Planet gasGiant = new Planet("Jupiter", 1.898e27, 7.785e11, 0, 0, 0, 13070, 0, 6.9911e7, Planet.PlanetType.GAS_GIANT);
        Planet dwarf = new Planet("Pluto", 1.309e22, 5.906e12, 0, 0, 0, 4740, 0, 1.188e6, Planet.PlanetType.DWARF);
        Planet iceGiant = new Planet("Neptune", 1.024e26, 4.495e12, 0, 0, 0, 5430, 0, 2.4622e7, Planet.PlanetType.ICE_GIANT);
        Planet exoplanet = new Planet("Kepler-22b", 3.6e25, 1e13, 0, 0, 0, 20000, 0, 1.5e7, Planet.PlanetType.EXOPLANET);

        assertEquals(Planet.PlanetType.GAS_GIANT, gasGiant.getPlanetType());
        assertEquals(Planet.PlanetType.DWARF, dwarf.getPlanetType());
        assertEquals(Planet.PlanetType.ICE_GIANT, iceGiant.getPlanetType());
        assertEquals(Planet.PlanetType.EXOPLANET, exoplanet.getPlanetType());
    }

    //used to test that the subclass is able to use methods from abstract class
    @Test
    void testPlanetInheritsBodyMethods() {
        Planet mars = new Planet("Mars", 6.39e23, 2.279e11, 100, 200, 500, 24070, 300, 3.39e6, Planet.PlanetType.TERRESTRIAL);

        assertEquals("Mars", mars.getName());
        assertEquals(6.39e23, mars.getMass());
        assertEquals(2.279e11, mars.getPositionX());
        assertEquals(100, mars.getPositionY());
        assertEquals(200, mars.getPositionZ());

        assertEquals(0, mars.getNetForceX());
        mars.updateNetForce(1000, 2000, 3000);
        assertEquals(1000, mars.getNetForceX());
        assertEquals(2000, mars.getNetForceY());
        assertEquals(3000, mars.getNetForceZ());

        mars.resetForce();
        assertEquals(0, mars.getNetForceX());
        assertEquals(0, mars.getNetForceY());
        assertEquals(0, mars.getNetForceZ());
    }

    @Test
    void testPlanetPositionAndVelocityUpdate() {
        Planet testPlanet = new Planet("Test", 1000, 0, 0, 0, 0, 0, 0, 100, Planet.PlanetType.TERRESTRIAL);

        testPlanet.updateNetForce(1000, 0, 0);
        testPlanet.updatePositionAndVelocityA(1);

        assertEquals(1, testPlanet.getVelocityX(), 0.001);
        assertEquals(1, testPlanet.getPositionX(), 0.001);
    }
}
