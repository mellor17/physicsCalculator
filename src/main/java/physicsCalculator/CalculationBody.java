package physicsCalculator;

import java.util.ArrayList;

import static physicsCalculator.CalculationEngine.isFinished;
import static physicsCalculator.InputUtility.scanner;

public class CalculationBody {

    /**
     * global variables or 'fields'
     */
    static final double massOfSun = 1.989e30;
    static final double massOfEarth = 5.972e24;
    static final double massOfMars = 6.39e23;
    static final double radiusOfSun = 6.955e8;
    static final double radiusOfEarth = 6.371e6;
    static final double radiusOfMars = 3.39e6;
    static final double yearInSeconds = 31_536_000; // note, you can use underscores to separate multiple digit numbers although these can be put anywhere as java ignores them
    static final double tenYearsInSeconds = yearInSeconds * 10;
    static final double fiveYearsInSeconds = yearInSeconds * 5;
    static final double sixMonthsInSeconds = yearInSeconds / 2;
    static final double oneMonthInSeconds = yearInSeconds / 12;

    /**
     * Method used to choose simulation options, may add more for different bodies.
     *
     */
    public static void nBodyProblem() { // I have also used the extract method command in intellij IDE  to create this method. See below for its full details
        while (!isFinished) {
            System.out.println("The N-body problem, in celestial mechanics, is predicting the motions of any number of masses (N) under mutual gravitational attraction, given their initial positions, velocities, and masses.");
            System.out.println("""
                    Select a simulation, or choose your own:");
                    1: Sun and Earth (Two-body)
                    2: Sun, Earth, and Mars (Three-body)
                    3: Choose your own simulation. (N-body)
                    """);

            // this line creates a new array list which can only hold the body object which has been defined in our body class, it can also hold any planet or star object as they are children of the body class
            ArrayList<Body> celestialBodies = new ArrayList<>();
            System.out.print("Choice: ");
            int presetChoice = scanner.nextInt();
            System.out.println("------------------------------------------------");

            if (presetChoice == 1) {
                Body sun = new Star("Sun", massOfSun, 0, 0, 0, 0, 0, 0, radiusOfSun, Star.StarType.YELLOW_DWARF); // initial velocity y for earth is the average speed is 29,780 is m/s
                Body earth = new Planet("Earth", massOfEarth, 1.496e11, 0, 0, 0, 29780, 0, radiusOfEarth, Planet.PlanetType.TERRESTRIAL); // initial x is the average distance from the earth to the sun, which is an astronomical unit (AU)

                celestialBodies.add(sun);
                celestialBodies.add(earth);

                CalculationEngine.calculateNBodyProblem(celestialBodies, 60, 31_536_000, false);

            } else if (presetChoice == 2) {
                Body sun = new Star("Sun", massOfSun, 0, 0, 0, 0, 0, 0, radiusOfSun, Star.StarType.YELLOW_DWARF);
                Body earth = new Planet("Earth", massOfEarth, 1.496e11, 0, 0, 0, 29780, 0, radiusOfEarth, Planet.PlanetType.TERRESTRIAL);
                Body mars = new Planet("Mars", massOfMars, 2.279e11, 0, 0, 0, 24070, 0, radiusOfMars, Planet.PlanetType.TERRESTRIAL);

                celestialBodies.add(sun);
                celestialBodies.add(earth);
                celestialBodies.add(mars);
                CalculationEngine.calculateNBodyProblem(celestialBodies, 60, 31_536_000, false);

            } else if (presetChoice == 3) {
                System.out.print("Enter the number of bodies (N): ");
                int numberOfBodies = scanner.nextInt();

                System.out.print("Enter the simulation time step (Δt): "); // delta t, this determines how much time in seconds that the program should move after each loop
                double timeStep = scanner.nextDouble();                    // delta t just means small step or change and is a greek letter

                System.out.println("""
                        Select a simulation time, or choose your own:");
                        1: 1 Year
                        2: 6 Months
                        3: 10 Years
                        4: 5 Years
                        5: 1 Month
                        6: Choose your own simulation time.
                        """);
                double totalTime;
                System.out.print("Choice: ");
                int simulationTimeResponse = scanner.nextInt();


                totalTime = switch (simulationTimeResponse) { // intelliJ idea IDE recommended this to me as a fix looks better than previous "normal switch"
                    case 1 -> yearInSeconds;
                    case 2 -> sixMonthsInSeconds;
                    case 3 -> tenYearsInSeconds;
                    case 4 -> fiveYearsInSeconds;
                    case 5 -> oneMonthInSeconds;
                    default -> {
                        System.out.print("Enter the total simulation duration in seconds (S):");
                        yield scanner.nextInt(); // this is used to return a value in the default case, so if we have
                    }
                };

                scanner.nextLine(); // added this to fix the input buffer consuming the newline character added when doing next int, cause it was breaking body name input section


                System.out.println("---------------------");
                System.out.println("Enter data for each body:");
                for (int i = 0; i < numberOfBodies; i++) {

                    String bodyName = String.valueOf(i + 1); // this sets the name of the body just to the number if no name is specified
                    System.out.println("Would you like to enter name for this body? (Y/N)");
                    String bodyHasNameResponse = scanner.nextLine();

                    if (bodyHasNameResponse.toLowerCase().contains("y")) {
                        System.out.println("Enter a name for the Body: ");
                        bodyName = scanner.nextLine();
                    }


                    System.out.println("--- Body: " + bodyName + " ---");


                    System.out.print("Mass (kg): ");
                    double mass = scanner.nextDouble();

                    System.out.print("Initial X Position (m): ");
                    double positionX = scanner.nextDouble();

                    System.out.print("Initial Y Position (m): ");
                    double positionY = scanner.nextDouble();

                    System.out.print("Initial Z Position (m): ");
                    double positionZ = scanner.nextDouble();

                    System.out.print("Initial X Velocity (m/s): ");
                    double velocityX = scanner.nextDouble();

                    System.out.print("Initial Y Velocity (m/s): ");
                    double velocityY = scanner.nextDouble();

                    System.out.print("Initial Z Velocity (m/s): ");
                    double velocityZ = scanner.nextDouble();

                    System.out.print("Body Radius (m): ");
                    double radius = scanner.nextDouble();

                    System.out.println("Is your body a Planet (1) or Star (2)?");
                    int objectType = scanner.nextInt();
                    scanner.nextLine();
                    if (objectType == 1) {
                        System.out.println("""
                                What is the type of the planet?
                                1. GAS GIANT
                                2. TERRESTRIAL
                                3. DWARF
                                4. ICE GIANT
                                5. EXOPLANET""");
                        int planetTypeChoice = scanner.nextInt();
                        scanner.nextLine();
                        Planet.PlanetType planetType = switch (planetTypeChoice) {
                            case 1 ->
                                    Planet.PlanetType.GAS_GIANT; // the reason why i have to access them like this is because enums cannot be instantiated, so I have to call it each time like this to access variables within enum
                            case 2 -> Planet.PlanetType.TERRESTRIAL;
                            case 3 -> Planet.PlanetType.DWARF;
                            case 4 -> Planet.PlanetType.ICE_GIANT;
                            case 5 -> Planet.PlanetType.EXOPLANET;
                            default -> null;
                        };
                        celestialBodies.add(new Planet(bodyName, mass, positionX, positionY, positionZ, velocityX, velocityY, velocityZ, radius, planetType));

                    } else {
                        System.out.println("""
                                What is the type of the star?
                                1. RED GIANT
                                2. BLUE GIANT
                                3. BROWN DWARF
                                4. WHITE DWARF
                                5. NEUTRON STAR
                                6. YELLOW DWARF""");
                        int starTypeChoice = scanner.nextInt();
                        scanner.nextLine();
                        Star.StarType starType = switch (starTypeChoice) {
                            case 1 -> Star.StarType.RED_GIANT;
                            case 2 -> Star.StarType.BLUE_GIANT;
                            case 3 -> Star.StarType.BROWN_DWARF;
                            case 4 -> Star.StarType.WHITE_DWARF;
                            case 5 -> Star.StarType.NEUTRON_STAR;
                            case 6 -> Star.StarType.YELLOW_DWARF;
                            default -> null;
                        };
                        celestialBodies.add(new Star(bodyName, mass, positionX, positionY, positionZ, velocityX, velocityY, velocityZ, radius, starType));

                    }

                    if (numberOfBodies > 1) {
                        System.out.println("-------- NEXT BODY --------");
                    }


                }
                CalculationEngine.calculateNBodyProblem(celestialBodies, timeStep, totalTime, false);
            }
        }
    }


    public static String randomMessageGenerator() {
        String[] randomMessages = {"Thank you for joining my program—can’t wait to have you back soon!", "Appreciate you being part of my program; hope we cross paths again soon!", "Thanks for taking part in my program; looking forward to seeing you again!", "Thanks for being here—hope to welcome you back to the program soon!", "Grateful you joined the program; I hope we get to do this again soon!", "Thanks for sticking with it—consider this your official permission to skive off home until next time.", "You were brilliant—almost suspiciously so. Pop back in before we start missing you.", "Right, that’s the lot mind how you go, and come back before my processors run out."};
        int randNum = (int) (Math.random() * randomMessages.length);
        return randomMessages[randNum];
    }

    public static String timeConversionMethod(int seconds) {
        return String.valueOf(seconds);
    }

}
