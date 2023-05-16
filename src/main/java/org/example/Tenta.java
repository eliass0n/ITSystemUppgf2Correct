package org.example;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.ElementNotFound;
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

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class Tenta {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public void runTenta() {
        try {
            setupBrowser();
            openWebsite();
            acceptCookies();
            clickStudentButton();
            clickMittLTU();
            login();
            navigateToTenta();
            switchToNewTab();
            clickSignInLink();
            loginSecondTime();
            clickActivityRegistration();
            captureScreen();
            closeCurrentPage();
            switchToPreviousTab();
            logout();
            waitForExit();
        } catch (ElementNotFound e) {
            logger.error("No such element found: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.getMessage());
        } finally {
            close();
        }
    }

    private void setupBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator\\Desktop\\New folder (2)\\chromedriver.exe");
        Configuration.browser = "chrome";
    }

    private void openWebsite() {
        open("https://www.ltu.se");
    }

    private void acceptCookies() {
        $("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll").click();
        logger.info("Accepted cookies");
    }

    private void clickStudentButton() {
        $x("//a[text()='Student']").click();
        logger.info("Clicked student option");
    }

    private void clickMittLTU() {
        $x("//a[text()='Mitt LTU']").click();
        logger.info("Clicked LTU button");
    }

    private void login() {
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

        $(byId("username")).shouldBe(visible).setValue(username);
        $(byId("password")).shouldBe(visible).setValue(password);
        $(byName("submit")).shouldBe(visible).click();
        logger.info("Logged in");
    }

    private void navigateToTenta() {
        $("a[id$='261']").click();
        $("a[id$='265']").click();
    }

    private void switchToNewTab() {
        List<String> handles = new ArrayList<>(WebDriverRunner.getWebDriver().getWindowHandles());
        Selenide.switchTo().window(handles.get(1));
        logger.info("Switched to the new tab");
    }

    private void clickSignInLink() {
        $("a[class='signin']").click();
    }

    private void loginSecondTime() {
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

        $(byId("login_username")).shouldBe(visible).setValue(username);
        $(byId("login_password")).shouldBe(visible).setValue(password);
        $("#login_button").pressEnter();
        logger.info("Logged in");
    }

    private void clickActivityRegistration() {
        $x("//html/body/div[1]/div[2]/div/div/ul/li[9]/a/em/b").click();
        logger.info("Clicked on activity registration");
    }

    private void captureScreen() {
        File targetDir = new File("target\\screenshots");
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenCapture = robot.createScreenCapture(screenRect);

            File file = new File("target\\screenshots\\final_examination.jpeg");
            ImageIO.write(screenCapture, "jpeg", file);
            System.out.println("Screen capture saved as " + file.getAbsolutePath());
        } catch (AWTException | IOException ex) {
            System.err.println(ex);
        }
    }

    private void closeCurrentPage() {
        $$("a.greenbutton span")
                .first()
                .shouldBe(visible)
                .click();
        logger.info("Closed the current page");
    }

    private void switchToPreviousTab() {
        List<String> handles = new ArrayList<>(WebDriverRunner.getWebDriver().getWindowHandles());
        Selenide.switchTo().window(handles.get(0));
        logger.info("Switched to the previous tab");
    }

    private void logout() {
        $("span.user-full-name").click();
        $("a[title='Logga ut']").click();
        logger.info("Logged out of LTU");
    }

    private void waitForExit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to exit...");
        scanner.nextLine();
    }
}
