/* Тесты на поисковую строку:
  - написать минимально необходимые тест кейсы для проверки поисковой строки вот тут 
  Логика: запрос => ожидание */

const {Builder, By, Key, until} = require('selenium-webdriver');
test = require('selenium-webdriver/testing'),
assert = require('assert');
const retailerBrand = "Market.Yandex";
const retailerTitle = "Интернет-магазин Яндекс.Маркет — покупки с быстрой доставкой";
const retailerURL = "https://market.yandex.ru/"
const browser = "chrome";
let driver;

describe(`Search on ${retailerBrand} tests`, function() {
    before(async () => driver = await  new Builder().forBrowser(browser).build());
    it(`Open ${retailerBrand}`, async () => {
        await driver.get(retailerURL);
        await driver.getTitle().then((title) => assert.equal(title, retailerTitle));    
    });

    /*it("search by query Corsair", function() {
        driver.get(retailerURL);
        //driver.findElement(By.xpath('/html/body/button[@data-r="search-button"][type="submit"]')).click();
        driver.wait(until.titleIs(), 1000);
        driver.close();
        assert.equal(pow(2, 3), 8);
    });

    it("search by query Corsair CMV16GX3M2A1600C11", function() {
        driver.wait(until.titleIs(retailerTitle), 1000);
        driver.findElement(By.xpath('/html/body/button[@data-r="search-button"][type="submit"]')).click();
        assert.equal(pow(2, 3), 8);
    });

    it("search by query", function() {
        assert.equal(pow(2, 3), 8);
    });*/

    after(() => await driver.quit());
});


