package org.example;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;


public class TentaTest {
    private Tenta tenta;

    @BeforeEach
    void setUp() {
        tenta = new Tenta();
    }

    @AfterEach
    void tearDown() {
        // Close the browser after each test
        Selenide.close();
    }

    @Test
    void testCaptureScreen() {
        tenta.runTenta();
        // Runs the Tenta program and checks if the Capture screen function works
    }
}
