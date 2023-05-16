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

    public void runCourse() {
        try {
            setupBrowser();
            openWebsite();
            acceptCookies();
            clickStudentButton();
            clickMittLTU();
            login();
            navigateToCourse();
            openNewTab();
            selectOptionInDropdown();
            clickSearchLabel();
            enterTextInSearchField();
            clickSearchButton();
            locateAndClickTargetElement();
            clickPdfDownloadLink();
            logger.info("Successfully downloaded PDF");

            waitForExit();
        } catch (ElementNotFound e) {
            logger.error("No such element found: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.getMessage());
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

    private void navigateToCourse() {
        $("a[id$='275']").click();
        $("a[id$='279']").click();
    }

    private void openNewTab() {
        List<String> handles = new ArrayList<>(WebDriverRunner.getWebDriver().getWindowHandles());
        Selenide.switchTo().window(handles.get(1));
        logger.info("Switched to the new tab");
    }

    private void selectOptionInDropdown() {
        SelenideElement dropdown = $("#utbkatsearch_filterKategori").shouldBe(visible);
        dropdown.selectOption("Alla program, kurser och examen");
        logger.info("Selected option in dropdown");
    }

    private void clickSearchLabel() {
        $x("//label[contains(text(), 'Sök på fritext')]").click();
        logger.info("Clicked search label");
    }

    private void enterTextInSearchField() {
        $("#fritext").setValue("Systemvetenskap");
        logger.info("Entered text in search field");
    }

    private void clickSearchButton() {
        $x("//*[@id='btnSearch']").click();
        logger.info("Clicked search button");
    }

    private void locateAndClickTargetElement() {
        $("html > body > main > div > div > div > div:nth-of-type(2) > div > div:nth-of-type(2) > div:nth-of-type(2) > div:nth-of-type(4) > div:nth-of-type(2) > div:nth-of-type(2) > a")
                .shouldBe(visible)
                .click();
        logger.info("Located and clicked target element");
    }

    private void clickPdfDownloadLink() {
        $("a[class='utbplan-pdf-link'] div")
                .shouldBe(visible)
                .click();
        logger.info("Clicked PDF download link");
    }

    private void waitForExit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to exit...");
        scanner.nextLine();
    }
}

