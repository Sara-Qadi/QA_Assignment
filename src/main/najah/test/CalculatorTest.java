package main.najah.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.Calculator;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)

@TestMethodOrder(OrderAnnotation.class)

class CalculatorTest {
    private Calculator calculator;

    @BeforeAll
    static void initAll() {
        System.out.println("Starting all tests...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("All tests completed.");
    }

    @BeforeEach
    void init() {
        calculator = new Calculator();
        System.out.println("Setup complete");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test finished");
    }

    @Test
    @Order(1)
    @DisplayName("Test Addition with Multiple Inputs")
    void testAddition() throws InterruptedException {
    	  Thread.sleep(1000);
        assertAll(
                () -> assertEquals(10, calculator.add(2, 3, 5)),
                () -> assertEquals(0, calculator.add()),
                () -> assertEquals(-5, calculator.add(-2, -3))
        );
    }

    @ParameterizedTest
    @Order(2)
    @DisplayName("Parameterized Test for Division")
    @CsvSource({"6,2,3", "10,5,2", "9,3,3"})
    void testDivision(int a, int b, int expected) throws InterruptedException {
    	  Thread.sleep(2000);
        assertEquals(expected, calculator.divide(a, b));
    }

    @Test
    @Order(3)
    @DisplayName("Test Division by Zero")
    void testDivisionByZero() {
        Exception exception = assertThrows(ArithmeticException.class, () -> calculator.divide(10, 0));
        assertEquals("Cannot divide by zero", exception.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("Factorial of Positive Numbers")
    void testFactorial() {
        assertAll(
                () -> assertEquals(1, calculator.factorial(0)),
                () -> assertEquals(1, calculator.factorial(1)),
                () -> assertEquals(120, calculator.factorial(5))
        );
    }

    @Test
    @Order(5)
    @DisplayName("Factorial of Negative Number Throws Exception")
    void testFactorialNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> calculator.factorial(-1));
        assertEquals("Negative input", exception.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("Factorial Calculation with Timeout")
    @Timeout(1) // Ensuring execution completes within 1 second
    void testFactorialTimeout() {
        assertEquals(3628800, calculator.factorial(10));
    }
    
    @Disabled("This test is failing to demonstrate a failure scenario")
    @Test
    @Order(7)
    @DisplayName("Test for an Incorrect Calculation")
    void testIncorrectCalculation() {
        // This test is expected to fail
        assertEquals(100, calculator.add(2, 3, 5)); // This is incorrect it should be 10
    }
 
}
