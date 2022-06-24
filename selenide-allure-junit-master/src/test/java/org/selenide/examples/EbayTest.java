    package org.selenide.examples;

    import com.codeborne.selenide.Configuration;
    import com.codeborne.selenide.junit5.TextReportExtension;
    import com.codeborne.selenide.logevents.SelenideLogger;
    import dev.failsafe.internal.util.Assert;
    import io.qameta.allure.selenide.AllureSelenide;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.openqa.selenium.By;

    import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
    import static com.codeborne.selenide.Condition.text;
    import static com.codeborne.selenide.Selenide.$;
    import static com.codeborne.selenide.Selenide.$$;
    import static com.codeborne.selenide.Selenide.open;

    @ExtendWith(TextReportExtension.class)
    class EbayTest {

      /* Тесты на поисковую строку:
      - написать минимально необходимые тест кейсы для проверки поисковой строки вот тут
      - Логика: запрос => ожидание */

        static final String _retailerBrand = "eBay";
        static final String _retailerTitle = "fashion sale online: Search Result | eBay";
        static final String _retailerURL = "https://www.ebay.com/";
        static final String _browser = "edge";
        static final String _keyword = "iPhone 13";
        static final String _product = "iPhone";
        static final String _model = "13";

        @BeforeEach
        public void setUp() {
        Configuration.browser = _browser;
            SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        }

        @Test
        void userCanSearchProduct() {
            open(_retailerURL);
            $(By.id("gh-ac")).val(_keyword).pressEnter();
            $$(By.className("s-item")).shouldBe(sizeGreaterThan(0));
        }

        @Test
        void userCanSearcAllProductsContainsKeyword() {
            open(_retailerURL);
            $(By.id("gh-ac")).val(_keyword).pressEnter();
            var products = $$(By.className("s-item"));
            for (var product: products) {
                var productName = product.getText().toLowerCase();
                Assert.isTrue(productName.contains(_product.toLowerCase()), "Product ins't found");
                Assert.isTrue(productName.contains(_model.toLowerCase()), "Model ins't found");
            }
        }
}
