package physicsCalculator;

public class Introduction {

    public static void callIntroductionMethod() {
        boolean stopProgram = false;
        boolean validAnswer = false;
        System.out.println("Welcome to Ben's Physics Calculator. Before we start, let's collect some data for the calculation.");
        while (!stopProgram) {
            System.out.println("------------------------------------------------");
            System.out.println("Please choose a calculation from the following numbers(1-4):\n Option 1: N-Body Gravitational Simulator  \n Option 2: Rocket Trajectory Simulator \n Option 3: Not yet implemented \n Option 4: Not yet implemented");
            System.out.println("------------------------------------------------");
            System.out.print("Choice: ");
            int option = InputUtility.scanner.nextInt();
            InputUtility.scanner.nextLine();

            switch (option) {
                case 1:
                    CalculationBody.nBodyProblem();
                    break;
                case 2:
                    System.out.println("NOT YET IMPLEMENTED");
                    break;
                case 3:
                    System.out.println("NOT YET IMPLEMENTED1");
                    break;
                case 4:
                    System.out.println("NOT YET IMPLEMENTED2");
                    break;
                default:
                    System.out.println(option + " is not a valid choice");
                    break;
            }

            InputUtility.scanner.nextLine(); // consumes input buffer to stop new lines from other inputs being taken
            do {
                System.out.println("Do you wish to proceed?");
                String proceed = InputUtility.scanner.nextLine();
                if (proceed.toLowerCase().startsWith("no")) {
                    stopProgram = true;
                    validAnswer = true;
                    System.out.println(CalculationBody.randomMessageGenerator());
                } else if (proceed.toLowerCase().startsWith("y")) {
                    System.out.println("You are continuing. Hooray!!");
                    validAnswer = true;
                } else {
                    System.out.println("Enter a valid answer.");
                }
            }
            while (!validAnswer);


        }

    }
}
