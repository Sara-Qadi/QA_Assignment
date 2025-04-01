package main.najah.test;

import main.najah.code.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceSimpleTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    @DisplayName("Valid Email Test")
    void testValidEmail() {
        assertTrue(userService.isValidEmail("test@example.com"), "Valid email should return true");
    }

    @Test
    @DisplayName("Invalid Email Test")
    void testInvalidEmail() {
        assertAll(
            () -> assertFalse(userService.isValidEmail("testexample.com"), "Email without @ should return false"),
            () -> assertFalse(userService.isValidEmail("test@com"), "Email without . after @ should return false"),
            () -> assertFalse(userService.isValidEmail(null), "Null email should return false")
        );
    }

    @ParameterizedTest
    @CsvSource({
        "admin, 1234, true",
        "admin, wrongpass, false",
        "user, 1234, false",
        "user, wrongpass, false"
    })
    @DisplayName("Parameterized Authentication Test")
    void testAuthentication(String username, String password, boolean expectedResult) {
        assertEquals(expectedResult, userService.authenticate(username, password),
                "Authentication should match expected result");
    }

    @Test
    @DisplayName("Authentication Timeout Test")
    void testAuthenticationTimeout() {
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> userService.authenticate("admin", "1234"),
                "Authentication should complete within 100ms");
    }

    @Test
    @Disabled("Fix: Change expected result to false since 'Admin' is not equal to 'admin'")
    @DisplayName("Intentionally Failing Test")
    void testCaseSensitivityFailure() {
        assertFalse(userService.authenticate("Admin", "1234"), "Authentication should be case-sensitive");
    }
}
