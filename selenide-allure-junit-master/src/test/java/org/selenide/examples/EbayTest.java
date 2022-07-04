    package org.selenide.examples;

    import com.codeborne.selenide.Configuration;
    import com.codeborne.selenide.junit5.TextReportExtension;
    import com.codeborne.selenide.logevents.SelenideLogger;
    import dev.failsafe.internal.util.Assert;
    import io.qameta.allure.Description;
    import io.qameta.allure.selenide.AllureSelenide;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.junit.jupiter.params.ParameterizedTest;
    import org.junit.jupiter.params.provider.ValueSource;
    import org.openqa.selenium.By;

    import static com.codeborne.selenide.CollectionCondition.anyMatch;
    import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
    import static com.codeborne.selenide.Condition.text;
    import static com.codeborne.selenide.Selenide.*;

    @ExtendWith(TextReportExtension.class)
    class EbayTest {

      /*    Rus: Тесты на поисковую строку:
      - написать минимально необходимые тест кейсы для проверки поисковой строки вот тут
      - Логика: запрос => ожидание 
            Eng: Test for searching bar:
        write minimum tests cases for checks of search bar here is
        Logic: request => waiting;

        Note: eBay isn't modern SPA application
       */

        static final String _retailerBrand = "eBay";
        static final String _retailerTitleEng = "fashion sale online: Search Result | eBay";
        static final String _retailerTitleRus = "Электроника, автомобили, мода, коллекционирование, купоны и другие товары | eBay";
        static final String _retailerSpecificProduct = "Для iPhone 13 Pro Max 12 11 Xs Xr 7 8 Plus Liquid силиконовый мягкий чехол телеф...";
        static final String _retailerURL = "https://www.ebay.com/";
        static  final String _retailerURLSpecificSearch = "https://www.ebay.com/sch/ebayadvsearch";
        static final String _browser = "safari";
        static final String _keyword = "iPhone 13";
        static final String _product = "iPhone";
        static final String _model = "13";
        static final String _color = "Black";

        @BeforeEach
        public void setUp() {
        Configuration.browser = _browser;
            SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        }

        /**
        * ORACLE 🏢🏡🏘️ comments 📝
        * Check ✅ the user can open web page or not 🙅‍🚫
        */
        @Test
        @Description("User can open web page $_retailerBrand")
        void userCanOpenPage() {
            open(_retailerURL);
            var title = $(By.tagName("title")).getText().toLowerCase();
            var expressionA = title.contains(_retailerTitleEng.toLowerCase());
            var expressionB = title.contains(_retailerTitleRus.toLowerCase());
            Assert.isTrue(expressionA || expressionB, "The title can't be found");
        }

        /**
         * ORACLE 🏢🏡🏘️ comments 📝
         * Check ✅ the user 👶👧👱‍👩‍🦰👱‍️👨‍🦳 can open web page or not 🙅‍🚫
         * @param titleTag Accept HTML tag that represents HTML tag Title
         * @return return true if everything is OK 👍 and the test's passed ✅
         *
         * <span color="yellow">TODO: Interpolation in Kotlin dosen't work 😫 The link 🌐 to article How Does String Interpolation Work in Kotlin? 🤔</span>
         * <span color="yellow">TODO: The link 🌐 to Interpolation in Kotlin: https://www.baeldung.com/kotlin/string-interpolation</span>
         * <span color="red">TODO: Allure report doesn't work  😫 if the test is return something ✅</span>
         */
        @Description("User can open web page $_retailerBrand")
        @ParameterizedTest()
        @ValueSource(strings = {"title"})
        void userCanOpenPage(String titleTag) {
            open(_retailerURL);
            var title = $(By.tagName(titleTag)).getText().toLowerCase();
            var expressionA = title.contains(_retailerTitleEng.toLowerCase());
            var expressionB = title.contains(_retailerTitleRus.toLowerCase());
            Assert.isTrue(expressionA || expressionB, "The title can't be found");

            // Note📝 : Allure report doesn't work if the test is return something
            // return expressionA || expressionB;
        }

        /// <summary>
        /// Microsoft 🏢🏡🏘️ comments 📝
        /// Search 🔍 for specific product 📦 by keyword🔐
        /// </summary>
        /// <param name=""></param>
        /// <returns></returns>
        @Test
        void userCanSearchProduct() {
            open(_retailerURL);
            $(By.id("gh-ac")).val(_keyword).pressEnter();
            $$(By.className("s-item")).shouldBe(sizeGreaterThan(0));
        }

        /// <summary>
        /// Microsoft 🏢🏡🏘️ comments 📝
        /// Search 🔍 for all products 📦 that match 👍 specific keyword 🔐
        /// </summary>
        /// <param name=""></param>
        /// <returns></returns>
        @Test
        void userCanSearcAllProductsContainsKeyword() {
            open(_retailerURL);
            $(By.id("gh-ac")).val(_keyword).pressEnter();
            var products = $$(By.className("s-item"));
            for (var product : products) {
                var productName = product.getText().toLowerCase();
                Assert.isTrue(productName.contains(_product.toLowerCase()), "Product isn't found");
                Assert.isTrue(productName.contains(_model.toLowerCase()), "Model isn't found");
            }
        }

        /// <summary>
        /// Microsoft 🏢🏡🏘️ comments 📝
        /// Search 🔍 for specific product 📦 on the page and using filters 🔍
        /// </summary>
        /// <param name=""></param>
        /// <returns></returns>
        @Test
        void userCanSearcSpecificProductOnPage() {
            open(_retailerURLSpecificSearch);

            // TODO 📋 Interpolation in Kotlin dosen't work ☹️ Link 🌐 to article How Does String Interpolation Work in Kotlin? 🤔 - https://www.baeldung.com/kotlin/string-interpolation
            // Search product by specific keyword
            $(By.id("_nkw")).val( _keyword + " " +  _color);
            // Only mobile phones
            $(By.id("e1-1")).selectOptionByValue("15032");
            sleep(5000);
            // Only new product
            $(By.id("LH_ItemConditionNew")).click();
            sleep(10000);
            // Push btn search product
            $(By.id("searchBtnLowerLnk")).click();

            var products = $$(By.className("vip"));

            products.shouldHave( anyMatch("",
                    x -> x.getText()
                            .toLowerCase()
                            .contains(_retailerSpecificProduct.toLowerCase())));
        }
}
