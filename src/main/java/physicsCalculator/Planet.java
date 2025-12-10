package physicsCalculator;


/**
 * Just for my understanding an extension of class makes it so that it inherits all fields (variables) and methods from
 * the original abstract class it has inherited from
 * */

public class Planet extends Body {
    private final PlanetType planetType;

    /**
     * This is a constructor this uses the variables in the class to create a new object that can be used across our programme
     * I also added Z in after making the initial program so that is why it is at the end.
     * Extended class from abstract body, super keyword is used in reference to the parent class, when used in a constructor as below
     * it calls the parent class constructor and takes all of the params from the planet and then passes them to the body
     * @param name
     * name of object
     * @param initial_mass
     * mass of obj
     * @param initial_x
     * x pos
     * @param initial_y
     * y pos
     * @param initial_z
     * z pos
     * @param initial_vx
     * velocity in x axis
     * @param initial_vy
     * velocity in y axis
     * @param initial_vz
     * velocity in z axis
     * @param radius
     * size of planet / distance from center to end
     * @param planetType
     * what type of planet it is and its properties
     */

    public Planet(String name , double initial_mass, double initial_x, double initial_y, double initial_z, double initial_vx, double initial_vy, double initial_vz,double radius , PlanetType planetType) {
        super(name,initial_mass, initial_x, initial_y, initial_z, initial_vx, initial_vy, initial_vz, radius);
        this.planetType = planetType;
    }

    public enum PlanetType {
        GAS_GIANT,
        TERRESTRIAL,
        DWARF,
        ICE_GIANT,
        EXOPLANET
    }

    public PlanetType getPlanetType() {
        return planetType;
    }
}
