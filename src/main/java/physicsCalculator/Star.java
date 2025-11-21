package physicsCalculator;

public class Star  extends  Body{
    private StarType starType;

    /**
     * This is a constructor this uses the variables in the class to create a new object that can be used across our programme
     * I also added Z in after making the initial program so that is why it is at the end. NOTE: this has been changed after a refactor
     *
     * @param name
     * @param initial_mass
     * @param initial_x
     * @param initial_y
     * @param initial_z
     * @param initial_vx
     * @param initial_vy
     * @param initial_vz
     *
     */
    public Star(String name, double initial_mass, double initial_x, double initial_y, double initial_z, double initial_vx, double initial_vy, double initial_vz, StarType starType) {
        super(name, initial_mass, initial_x, initial_y, initial_z, initial_vx, initial_vy, initial_vz);
        this.starType = starType;
    }

    public enum StarType {
        RED_GIANT,
        BLUE_GIANT,
        BROWN_DWARF,
        WHITE_DWARF,
        NEUTRON_STAR,
        YELLOW_DWARF
    }
}
