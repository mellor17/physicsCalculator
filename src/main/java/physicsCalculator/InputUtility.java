package physicsCalculator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class  InputUtility {
    public static Scanner scanner = new Scanner(System.in); // implemented this so i don't have to keep declaring scanner each time and can just reference it \_//_/ ⍩

    // useful method which takes a string param as the output then returns value with added input checking
    public static double inputCheckerDouble(String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again!");
                scanner.nextLine(); // this clears the input buffer of any leftover stuff
            }

        }
    }

    public static int inputCheckerInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again!");
                scanner.nextLine();
            }

        }
    }

    public static String inputCheckerString(String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again!");
                scanner.nextLine();
            }

        }
    }
}
