package physicsCalculator;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * found this idea on how to test that the input checkers work as expected online, see below
 * @link https://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input#:~:text=InputStream%20sysInBackup%20%3D%20System.in%3B%20//%20backup%20System.in%20to%20restore%20it%20later
 * */
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



public class InputUtilityTest {

    InputStream sysInBackup = System.in; // backup System.in to restore it

    // i have used this annotation in TS testing at work, which is why i know how to use it
    @AfterEach // this is an annotation that basically resets the scanner class to how it was before after each test run to ensure we do not affect other parts of the program
    void restoreSystemIn() { // by changing the input stream to our test value
        System.setIn(sysInBackup);
        InputUtility.scanner = new Scanner(System.in);
    }

    // super simple test to ensure that the scanner has been globally initialised
    @Test
    void assertThatScannerShouldBeInitialised() {
        assertNotNull(InputUtility.scanner);
    }
    /**
     * This is an explanation of how this test works for my own learning and future ref:
     * the bytearray input stream is essentially just a way to trick java into thinking this is keyboard input
     *  the newline in each input is essentially just the test pressing the enter key
     *  replaces the keyboard input with our new Bytearray input class
     *  finally we test that our methods work by calling them and asserting that we get the correct result
     *  * */
    @Test
    void assertThatInputCheckerReturnDoubleOnCorrectInput() {
        ByteArrayInputStream in = new ByteArrayInputStream("3.14\n".getBytes());
        System.setIn(in);
        InputUtility.scanner = new Scanner(System.in); // this then reads from our new input stream and should pass it into the checker method

        double result = InputUtility.inputCheckerDouble("Enter a double:");
        assertEquals(3.14, result);
    }


    // the rest of these tests work essentially the same as the ones above
    @Test
    void assertThatInputCheckerReturnIntOnCorrectInput() {
        ByteArrayInputStream in = new ByteArrayInputStream("42\n".getBytes());
        System.setIn(in);
        InputUtility.scanner = new Scanner(System.in);

        int result = InputUtility.inputCheckerInt("Enter an int:");
        assertEquals(42, result);
    }

    @Test
    void assertThatInputCheckerReturnStringOnCorrectInput() {
        ByteArrayInputStream in = new ByteArrayInputStream("Hello World\n".getBytes());
        System.setIn(in);
        InputUtility.scanner = new Scanner(System.in);

        String result = InputUtility.inputCheckerString("Enter a string:");
        assertEquals("Hello World", result);
    }

}
