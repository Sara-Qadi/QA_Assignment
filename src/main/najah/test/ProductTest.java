package main.najah.test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.Product;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product product;

    @BeforeEach
    void init() {
        product = new Product("Laptop", 1000);
        System.out.println("Setup complete");
    }

    @Test
    @DisplayName("Valid Product Creation")
    void testValidProductCreation() {
        assertAll(
                () -> assertEquals("Laptop", product.getName()),
                () -> assertEquals(1000, product.getPrice()),
                () -> assertEquals(0, product.getDiscount())
        );
    }

    @Test
    @DisplayName("Invalid Product Price")
    void testInvalidProductPrice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Product("Phone", -100));
        assertEquals("Price must be non-negative", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Valid Discount Application")
    @CsvSource({"10,900", "20,800", "50,500"})
    void testApplyValidDiscount(double discount, double expectedPrice) {
        product.applyDiscount(discount);
        assertEquals(expectedPrice, product.getFinalPrice());
    }

    @Test
    @DisplayName("Invalid Discount Application")
    void testApplyInvalidDiscount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(60));
        assertEquals("Invalid discount", exception.getMessage());
    }

    @Test
    @DisplayName("Final Price Calculation with Timeout")
    @Timeout(1)
    void testFinalPriceTimeout() {
        product.applyDiscount(20);
        assertEquals(800, product.getFinalPrice());
    }

    @Disabled("Fails because expected price rounding is different. Fix by adjusting precision.")
    @Test
    @DisplayName("Intentionally Failing Test")
    void testFailingFinalPrice() {
        product.applyDiscount(33.33);
        assertEquals(666.67, product.getFinalPrice()); // Precision issue
    }
}