package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.ElementNotFound;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;



public class Course {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        try {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator\\Desktop\\New folder (2)\\chromedriver.exe");

            Configuration.browser = "chrome";

            // Open the website using Selenide
            open("https://www.ltu.se");

            $("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll").click();
            logger.info("Accept cookies");

            // Click the "Student" button
            $x("//a[text()='Student']").click();
            logger.info("Clicking student option");

            $x("//a[text()='Mitt LTU']").click();
            logger.info("Clicking LTU button");

            String username = "";
            String password = "";

            try {
                String jsonString = new String(Files.readAllBytes(Paths.get("c:/temp/ltu.json")));
                JSONObject jsonObject = new JSONObject(jsonString);
                username = jsonObject.getJSONObject("ltuCredentials").getString("Användarid");
                password = jsonObject.getJSONObject("ltuCredentials").getString("Lösenord");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            // Fill in the login form
            $(byId("username")).shouldBe(visible).setValue(username);
            $(byId("password")).shouldBe(visible).setValue(password);
            $(byName("submit")).shouldBe(visible).click();
            logger.info("Loggar in");

            $("a[id$='275']").click();


            $("a[id$='279']").click();

            List<String> handles = new ArrayList<>(WebDriverRunner.getWebDriver().getWindowHandles());

            // Byt till den andra fliken (index 1)
            Selenide.switchTo().window(handles.get(1));
            logger.info("Switches to the new tab");

            Thread.sleep(3000);

            // Find the dropdown element by its ID
            SelenideElement dropdown = $("#utbkatsearch_filterKategori").shouldBe(visible);
            logger.info("Found dropdown");

            // Create a Select object from the dropdown element
            dropdown.selectOption("Alla program, kurser och examen");
            logger.info("Selected option in dropdown");

            Thread.sleep(3000);


            // Click the "Search on text" label
            $x("//label[contains(text(), 'Sök på fritext')]").click();
            logger.info("Clicked search label");

            // Enter text in the "Sök på fritext" field using the YourPage class
            $("#fritext").setValue("Systemvetenskap");
            logger.info("Entered text in search field");

            // Click the search button
            $x("//*[@id='btnSearch']").click();
            logger.info("Clicked search button");

            // Locate and click the target element
            $("html > body > main > div > div > div > div:nth-of-type(2) > div > div:nth-of-type(2) > div:nth-of-type(2) > div:nth-of-type(4) > div:nth-of-type(2) > div:nth-of-type(2) > a")
                    .shouldBe(visible)
                    .click();
            logger.info("Located and clicked target element");

            $("a[class='utbplan-pdf-link'] div")
                    .shouldBe(visible)
                    .click();
            logger.info("Clicked PDF download link");

            logger.info("Sucessfully downloaded pdf");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Press enter to exit...");
            scanner.nextLine();

        } catch (ElementNotFound e) {
            logger.error("No such element found: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.getMessage());

        }
    }
}
