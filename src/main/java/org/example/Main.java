package org.example;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.visible;
import java.util.Scanner;
import java.io.IOException;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ex.ElementNotFound;
import org.slf4j.LoggerFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import static com.codeborne.selenide.Selectors.byName;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import com.codeborne.selenide.WebDriverRunner;




public class Main {
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
            logger.info("Click the student button");

            $x("//a[text()='Mitt LTU']").click();
            logger.info("clicks on my ltu");

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
            $("#username").shouldBe(visible).setValue(username);
            $("#password").shouldBe(visible).setValue(password);
            $(byName("submit")).shouldBe(visible).click();
            logger.info("Sign in");

            $("a[id*='271']").click();
            logger.info("Click on certificate");

            // Hämta alla fönsterhanterare
            List<String> handles = new ArrayList<>(WebDriverRunner.getWebDriver().getWindowHandles());

            // Byt till den andra fliken (index 1)
            Selenide.switchTo().window(handles.get(1));
            logger.info("Byter till den nya fliken");

            $x("//a[contains(@aria-label, 'lärosäte')]").click();
            logger.info("Clicks on university");

            $("#searchinput").setValue("ltu");
            logger.info("Entering ltu");

            $("li[aria-label='Select Lulea University of Technology']").click();
            logger.info("Clicks on ltu that comes up");

            $x("//button[contains(@role, 'button')]").click();
            logger.info("Clicks on menu");

            $x("//a[@href='/student/app/studentwebb/intyg']").click();
            logger.info("Click on certificate");

            $x("//button[@title='Skapa intyg']").click();
            logger.info("Creates certificate");

            $x("//select[@id='intygstyp']").selectOption("Registreringsintyg");
            logger.info("Clicks on certificate type and takes registration certificate");

            $x("//input[@id='allaRegistreringarGrupperdePaProgramRadio']").click();
            logger.info("All registrations");
            Thread.sleep(2000);

            Selenide.executeJavaScript("arguments[0].click();", $x("//button[contains(@class, 'btn-ladok-brand')]"));
            logger.info("Creates certificate");
            Thread.sleep(2000);

            $x("//a[contains(text(),'Registreringsintyg')]").click();
            logger.info("download registers");
            Thread.sleep(2000);

            $x("//button[contains(@role, 'button')]").click();
            logger.info("Clicks on menu");

            $x("/html/body/ladok-root/div/ladok-sido-meny/nav/div[2]/ul[3]/li/a").click();
            logger.info("Log out");

            close();
            logger.info("Closes the current page");

            switchTo().window(0);
            logger.info("Switches to the previous tab");

            // Klicka på avatar-länken
            $x("//span[@class='user-full-name']").click();
            // Klicka på logout-ikonen
            $x("//a[@title='Logga ut']").click();
            logger.info("Logging out of ltu");

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

