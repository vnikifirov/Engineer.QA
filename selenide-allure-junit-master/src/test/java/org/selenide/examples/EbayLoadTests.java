package org.selenide.examples;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Stopwatch;
import com.codeborne.selenide.logevents.SelenideLogger;
import dev.failsafe.internal.util.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EbayLoadTests {
    static final String _retailerURL = "https://www.ebay.com/";

    @BeforeEach
    public void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }

    /**
     * ORACLE 🏢🏡🏘️ comments 📝
     * Check ✅ the user can open web page or not 🙅‍🚫
     */
    @Test
    @Description("User can open web page $_retailerBrand")
    void userCanOpenPage() {
        var response = given().when().get(_retailerURL);
        assertEquals(200, response.then().extract().statusCode());
    }

    /**
     * ORACLE 🏢🏡🏘️ comments 📝
     * Measure performance with content
     *
    @Test
    @Description("User can open web page $_retailerBrand")
    void OpenPageMeasurePerformanceWithContent() {
        var sw = new Stopwatch(3100);
        var response = given().when().get(_retailerURL);
        var isReached = sw.isTimeoutReached();
        assertEquals(isReached, false);
    }*/

    /**
     * ORACLE 🏢🏡🏘️ comments 📝
     * Measure performance without content
     */
    @Test
    @Description("User can open web page $_retailerBrand")
    void OpenPageMeasurePerformanceWithoutContent() {
        var sw = new Stopwatch(100);
        var response = given().when().options(_retailerURL);
        var isReached = sw.isTimeoutReached();
        assertEquals(isReached, false);
    }
}
