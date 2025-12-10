package physicsCalculator;

import java.awt.*;
import java.util.ArrayList;

import static physicsCalculator.CalculationEngine.isFinished;
import static physicsCalculator.CalculationEngine.watchSimulation;
import static physicsCalculator.InputUtility.*;


public class CalculationBody {

    /**
     * global variables or 'fields'
     * Also, these are approximations for time not exact figures, a year in seconds as been rounded up
     * The radii should be fairly accurate however
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
     */
    public static void nBodyProblem() { // I have also used the extract method command in intellij IDE  to create this method. See below for its full details
        while (!isFinished) {
            System.out.println("The N-body problem, in celestial mechanics, is predicting the motions of any number of masses (N) under mutual gravitational attraction, given their initial positions, velocities, and masses.");
            System.out.println("""
                    Select a simulation, or choose your own:
                    1: Sun and Earth (Two-body)
                    2: Sun, Earth, and Mars (Three-body)
                    3: Choose your own simulation. (N-body)
                    """);

            // this line creates a new array list which can only hold the body object which has been defined in our body class, it can also hold any planet or star object as they are children of the body class
            ArrayList<Body> celestialBodies = new ArrayList<>();
            int presetChoice = inputCheckerInt("Choice: ");
            System.out.println("------------------------------------------------");

            if (presetChoice == 1) {
                watchSimulationCheck();
                Body sun = new Star("Sun", massOfSun, 0, 0, 0, 0, 0, 0, radiusOfSun, Star.StarType.YELLOW_DWARF); // initial velocity y for earth is the average speed is 29,780 is m/s
                Body earth = new Planet("Earth", massOfEarth, 1.496e11, 0, 0, 0, 29780, 0, radiusOfEarth, Planet.PlanetType.TERRESTRIAL); // initial x is the average distance from the earth to the sun, which is an astronomical unit (AU)

                celestialBodies.add(sun);
                celestialBodies.add(earth);

                CalculationEngine.calculateNBodyProblem(celestialBodies, 180, oneMonthInSeconds, false);

            } else if (presetChoice == 2) {
                watchSimulationCheck();
                Body sun = new Star("Sun", massOfSun, 0, 0, 0, 0, 0, 0, radiusOfSun, Star.StarType.YELLOW_DWARF);
                Body earth = new Planet("Earth", massOfEarth, 1.496e11, 0, 0, 0, 29780, 0, radiusOfEarth, Planet.PlanetType.TERRESTRIAL);
                Body mars = new Planet("Mars", massOfMars, 2.279e11, 0, 0, 0, 24070, 0, radiusOfMars, Planet.PlanetType.TERRESTRIAL);

                celestialBodies.add(sun);
                celestialBodies.add(earth);
                celestialBodies.add(mars);
                CalculationEngine.calculateNBodyProblem(celestialBodies, 60, 31_536_000, false);

            } else if (presetChoice == 3) {
                watchSimulationCheck();
                int numberOfBodies = inputCheckerInt("Enter the number of bodies (N): ");

                // delta t, this determines how much time in seconds that the program should move after each loop
                // delta t just means small step or change and is a greek letter
                double timeStep = inputCheckerDouble("Enter the simulation time step in seconds (Δt): ");

                System.out.println("""
                        Select a simulation time, or choose your own:
                        1: 1 Year
                        2: 6 Months
                        3: 10 Years
                        4: 5 Years
                        5: 1 Month
                        6: Choose your own simulation time.
                        """);
                double totalTime;

                int simulationTimeResponse = inputCheckerInt("Choice: ");


                totalTime = switch (simulationTimeResponse) { // intelliJ idea IDE recommended this to me as a fix looks better than previous "normal switch"
                    case 1 -> yearInSeconds;
                    case 2 -> sixMonthsInSeconds;
                    case 3 -> tenYearsInSeconds;
                    case 4 -> fiveYearsInSeconds;
                    case 5 -> oneMonthInSeconds;
                    default ->
                            inputCheckerInt("Enter the total simulation duration in seconds (S):"); // this is used to return a value in the default case, if we didn't have a method here we would use yield
                };


                System.out.println("---------------------");
                System.out.println("Enter data for each body:");
                for (int i = 0; i < numberOfBodies; i++) {

                    String bodyName = String.valueOf(i + 1); // this sets the name of the body just to the number if no name is specified
                    String bodyHasNameResponse = inputCheckerString("Would you like to enter name for this body? (Y/N)");

                    if (bodyHasNameResponse.toLowerCase().contains("y")) {
                        bodyName = inputCheckerString("Enter a name for this body: ");
                    }


                    System.out.println("--- Body: " + bodyName + " ---");

                    double mass = inputCheckerDouble("Mass (kg): ");

                    double positionX = inputCheckerDouble("Initial X Position (m): ");

                    double positionY = inputCheckerDouble("Initial Y Position (m): ");

                    double positionZ = inputCheckerDouble("Initial Z Position (m): ");

                    double velocityX = inputCheckerDouble("Initial X Velocity (m/s): ");

                    double velocityY = inputCheckerDouble("Initial Y Velocity (m/s): ");

                    double velocityZ = inputCheckerDouble("Initial Z Velocity (m/s): ");

                    double radius = inputCheckerDouble("Body Radius (m): ");

                    int objectType = inputCheckerInt("Is your body a Planet (1) or Star (2)?");

                    if (objectType == 1) {
                        int planetTypeChoice = inputCheckerInt("""
                                What is the type of the planet?
                                1. GAS GIANT
                                2. TERRESTRIAL
                                3. DWARF
                                4. ICE GIANT
                                5. EXOPLANET""");
                        Planet.PlanetType planetType = switch (planetTypeChoice) {
                            case 1 -> Planet.PlanetType.GAS_GIANT; // the reason why i have to access them like this is because enums cannot be instantiated, so I have to call it each time like this to access variables within enum
                            case 2 -> Planet.PlanetType.TERRESTRIAL;
                            case 3 -> Planet.PlanetType.DWARF;
                            case 4 -> Planet.PlanetType.ICE_GIANT;
                            case 5 -> Planet.PlanetType.EXOPLANET;
                            default -> null;
                        };
                        celestialBodies.add(new Planet(bodyName, mass, positionX, positionY, positionZ, velocityX, velocityY, velocityZ, radius, planetType));

                    } else {
                        int starTypeChoice = inputCheckerInt("""
                                What is the type of the star?
                                1. RED GIANT
                                2. BLUE GIANT
                                3. BROWN DWARF
                                4. WHITE DWARF
                                5. NEUTRON STAR
                                6. YELLOW DWARF""");

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


    //plays when the simulation ends just gives a random cheesy message :-)
    public static String randomMessageGenerator() {
        String[] randomMessages = {"Thank you for joining my programme—can’t wait to have you back soon!", "Appreciate you being part of my program; hope we cross paths again soon!", "Thanks for taking part in my programme; looking forward to seeing you again!", "Thanks for being here—hope to welcome you back to the programme soon!", "Grateful you joined the programme; I hope we get to do this again soon!", "Thanks for sticking with it—consider this your official permission to skive off home until next time.", "You were brilliant—almost suspiciously so. Pop back in before we start missing you.", "Right, that’s the lot mind how you go, and come back before my processors run out."};
        int randNum = (int) (Math.random() * randomMessages.length);
        return randomMessages[randNum];
    }

    /**
     * Converts seconds counter in engine into a much more readable format
     * Example: 31_536_000 should equal 1 year
     * here we divide the time by the total time left and cast it as an integer so we get the whole figure of the number of years for example
     * this same logic is repeated throughout so we can have the whole number of the time measurement barring seconds
     */
    public static String timeConversionMethod(double seconds) {
        String result = "";

        // this will be used on the total duration of our engine to then return a string with years, months weeks and days
        double timeLeft = seconds;

        int years = (int) (timeLeft / yearInSeconds);
        timeLeft = timeLeft - (years * yearInSeconds);

        int months = (int) (timeLeft / oneMonthInSeconds);
        timeLeft = timeLeft - (months / oneMonthInSeconds);

        int weeks = (int) (timeLeft / 604800);
        timeLeft = timeLeft - (weeks * 604800);

        int days = (int) (timeLeft / 86400);
        timeLeft = timeLeft - (days * 86400);
        

        int hours = (int) (timeLeft / 3600);
        timeLeft = timeLeft - (hours * 3600);
        
        int minutes = (int) (timeLeft / 60);
        timeLeft = timeLeft - (minutes * 60);
        
        int secs = (int) timeLeft;

        if (years > 0) {
            result = result + years + (years == 1 ? " year, " : " years, "); // this uses a ternary operator which is just essentially a shorthand if statement
        }
        if (months > 0) {
            result = result + months + (months == 1 ? " month, " : " months, "); // example: (if months is equal to one then we append month, else append months)
        }
        if (weeks > 0) {
            result = result + weeks + (weeks == 1 ? " week, " : " weeks, ");
        }
        if (days > 0) {
            result = result + days + (days == 1 ? " day, " : " days ");
        }
        if (hours > 0) {
            result = result + hours + (hours == 1 ? " hour, " : " hours, ");

        }
        if (minutes > 0) {
            result = result + minutes + (minutes == 1 ? " minute, " : " minutes, ");

        }
        if (secs > 0) {
            result = result + secs + (secs == 1 ? " second, " : " seconds, ");
        }
        
        if (result.endsWith(", ")) { // this just checks if we have trailing commas at the end and removes that plus whitespace
            result = result.substring(0, result.length() - 2);
        }
        
        if (result.equals("")) { // used to check whether time is empty
            result = "0 seconds";
        }
        
        return result;
    }

    public static void watchSimulationCheck() {
        int response = inputCheckerInt("Would you like to watch the simulation print out (1) or have it finish instantly?(2)");
        try {
            if (response == 1) {
                watchSimulation = true;
            } else if (response == 2) {
                watchSimulation = false;
            }
        } catch (Exception e) {
            System.out.println("Invalid input, please try again.");
        }
    }
}
