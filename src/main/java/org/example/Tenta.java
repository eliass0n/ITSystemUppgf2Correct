import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.ElementNotFound;
import org.example.Main;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byId;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Tenta {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

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
            // Do something on the website, for example print the page title

            $x("//a[text()='Mitt LTU']").click();


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

            $("a[id$='261']").click();
            $("a[id$='265']").click();

            List<String> handles = new ArrayList<>(WebDriverRunner.getWebDriver().getWindowHandles());

            // Byt till den andra fliken (index 1)
            Selenide.switchTo().window(handles.get(1));
            logger.info("Byter till den nya fliken");

            $("a[class='signin']").click();

            // Fill in the login form
            $(byId("login_username")).shouldBe(visible).setValue(username);
            $(byId("login_password")).shouldBe(visible).setValue(password);
            getWebDriver().switchTo().activeElement().sendKeys(Keys.ENTER);
            logger.info("Loggar in");

            $x("//html/body/div[1]/div[2]/div/div/ul/li[9]/a/em/b").click();
            logger.info("Klickar på aktivitetsanmälan");

            File targetDir = new File("target\\screenshots");
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }
            try {
                // Create a robot instance
                Robot robot = new Robot();
                // Capture the screen
                Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                BufferedImage screenCapture = robot.createScreenCapture(screenRect);

                // Save the captured screen as a JPEG file
                File file = new File("target\\screenshots\\final_examination.jpeg");
                ImageIO.write(screenCapture, "jpeg", file);
                System.out.println("Screen capture saved as " + file.getAbsolutePath());

            } catch (AWTException | IOException ex) {
                System.err.println(ex);
            }

            $$("a.greenbutton span")
                    .first()
                    .shouldBe(visible)
                    .click();
            logger.info("Closes the current page");

            switchTo().window(handles.get(0));
            logger.info("Switches to the previous tab");

            // Klicka på avatar-länken
            $("span.user-full-name").click();
            // Klicka på logout-ikonen
            $("a[title='Logga ut']").click();
            logger.info("Logging out of ltu");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Press enter to exit...");
            scanner.nextLine();


        } catch (ElementNotFound e) {
            logger.error("No such element found: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.getMessage());
        } finally {
            close();
        }
    }
}