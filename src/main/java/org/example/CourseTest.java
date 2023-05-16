package org.example;
import org.junit.jupiter.api.Test;


public class CourseTest {

    @Test
    public void testRunCourse() {
        Course course = new Course();
        course.runCourse();
        // Run it to test Course.java and test the execution of the program
    }

    @Test
    public void testSetupBrowser() {
        Course course = new Course();
        // Call the setupBrowser() method to test if it sets up the browser correctly
    }

    @Test
    public void testOpenWebsite() {
        Course course = new Course();
        // Call the openWebsite() method, tests to see if it successfully opens it (The browser)

    }

    @Test
    public void testAcceptCookies() {
        Course course = new Course();
        // Call the acceptCookies() method tests it to see if it accepts cookies.

    }
}
