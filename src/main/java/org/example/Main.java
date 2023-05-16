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
        Main main = new Main();
        main.execute();
    }

    public void execute() {
        try {
            setupConfiguration();
            acceptCookies();
            clickStudentButton();
            clickMittLTU();
            String username = readUsername();
            String password = readPassword();
            signIn(username, password);
            clickCertificate();
            switchToNewTab();
            clickUniversity();
            enterLTU();
            selectLTU();
            clickMenu();
            clickCertificateMenu();
            selectRegistrationCertificate();
            clickAllRegistrations();
            createCertificate();
            downloadRegisters();
            clickMenuButton();
            logOut();
            closeCurrentPage();
            switchToPreviousTab();
            clickAvatar();
            logOutLTU();

            waitForExit();
        } catch (ElementNotFound e) {
            logger.error("No such element found: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.getMessage());
        }
    }

    private void setupConfiguration() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator\\Desktop\\New folder (2)\\chromedriver.exe");
        Configuration.browser = "chrome";
    }

    private void acceptCookies() {
        open("https://www.ltu.se");
        $("#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll").click();
        logger.info("Accept cookies");
    }

    private void clickStudentButton() {
        $x("//a[text()='Student']").click();
        logger.info("Click the student button");
    }

    private void clickMittLTU() {
        $x("//a[text()='Mitt LTU']").click();
        logger.info("clicks on my ltu");
    }

    private String readUsername() throws IOException, JSONException {
        String jsonString = new String(Files.readAllBytes(Paths.get("c:/temp/ltu.json")));
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getJSONObject("ltuCredentials").getString("Användarid");
    }

    private String readPassword() throws IOException, JSONException {
        String jsonString = new String(Files.readAllBytes(Paths.get("c:/temp/ltu.json")));
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getJSONObject("ltuCredentials").getString("Lösenord");
    }

    private void signIn(String username, String password) {
        $("#username").shouldBe(visible).setValue(username);
        $("#password").shouldBe(visible).setValue(password);
        $(byName("submit")).shouldBe(visible).click();
        logger.info("Sign in");
    }

    private void clickCertificate() {
        $("a[id*='271']").click();
        logger.info("Click on certificate");
    }

    private void switchToNewTab() {
        List<String> handles = new ArrayList<>(WebDriverRunner.getWebDriver().getWindowHandles());
        Selenide.switchTo().window(handles.get(1));
        logger.info("Switch to the new tab");
    }


    private void clickUniversity() {
        $x("//a[contains(@aria-label, 'lärosäte')]").click();
        logger.info("Clicks on university");
    }

    private void enterLTU() {
        $("#searchinput").setValue("ltu");
        logger.info("Entering ltu");
    }

    private void selectLTU() {
        $("li[aria-label='Select Lulea University of Technology']").click();
        logger.info("Clicks on ltu that comes up");
    }

    private void clickMenu() {
        $x("//button[contains(@role, 'button')]").click();
        logger.info("Clicks on menu");
    }

    private void clickCertificateMenu() {
        $x("//a[@href='/student/app/studentwebb/intyg']").click();
        logger.info("Click on certificate");
    }

    private void selectRegistrationCertificate() {
        $x("//select[@id='intygstyp']").selectOption("Registreringsintyg");
        logger.info("Clicks on certificate type and takes registration certificate");
    }

    private void clickAllRegistrations() {
        $x("//input[@id='allaRegistreringarGrupperdePaProgramRadio']").click();
        logger.info("All registrations");
    }

    private void createCertificate() throws InterruptedException {
        Selenide.executeJavaScript("arguments[0].click();", $x("//button[contains(@class, 'btn-ladok-brand')]"));
        logger.info("Creates certificate");
        Thread.sleep(2000);
    }

    private void downloadRegisters() throws InterruptedException {
        $x("//a[contains(text(),'Registreringsintyg')]").click();
        logger.info("download registers");
        Thread.sleep(2000);
    }

    private void clickMenuButton() {
        $x("//button[contains(@role, 'button')]").click();
        logger.info("Clicks on menu");
    }

    private void logOut() {
        $x("/html/body/ladok-root/div/ladok-sido-meny/nav/div[2]/ul[3]/li/a").click();
        logger.info("Log out");
    }

    private void closeCurrentPage() {
        close();
        logger.info("Closes the current page");
    }

    private void switchToPreviousTab() {
        switchTo().window(0);
        logger.info("Switches to the previous tab");
    }

    private void clickAvatar() {
        $x("//span[@class='user-full-name']").click();
    }

    private void logOutLTU() {
        $x("//a[@title='Logga ut']").click();
        logger.info("Logging out of ltu");
    }

    private void waitForExit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to exit...");
        scanner.nextLine();
    }
}
