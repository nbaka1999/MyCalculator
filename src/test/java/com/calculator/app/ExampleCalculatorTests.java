package com.calculator.app;

import com.calculator.app.pages.CalculatorPage;
import com.calculator.app.pages.ResultPage;
import org.openqa.selenium.By;
import org.testng.Assert;

import org.testng.annotations.Test;

import java.util.Random;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;


public class ExampleCalculatorTests {

    @Test
    public void TestCheckMessageNecessityToFillInTheField() {
        CalculatorPage page = open("https://tarifer.ru/calculator", CalculatorPage.class);

        page.selectRandomRegion();
        page.selectRandomOperator();

        page.pressSubmitButton();

        $(By.tagName("h3")).shouldHave((text("Необходимо заполнить в форме хотя бы одно поле.")));
    }

    @Test
    public void TestCheckingTheCorrectnessOfDisplayingCitiesInDifferentPlaces() {
        CalculatorPage page = open("https://tarifer.ru/calculator", CalculatorPage.class);

        page.selectRandomRegion();
        page.selectRandomOperator();

        String region = page.getSelectedRegion();

        page.enterInet("123");

        ResultPage ResultPage = page.pressSubmitButton();

        $(By.tagName("h3"))
                .shouldHave((text("10 наиболее подходящих тарифных планов в регионе " + region + ".")));

        ResultPage.selectRandomTariffInSearchResult();

        $(By.xpath("//ol[@class='breadcrumb']/li[4]/a")).shouldHave((text(region)));
    }

    @Test
    public void TestCheckResultOfSearching() {
        CalculatorPage page = open("https://tarifer.ru/calculator", CalculatorPage.class);

        for (int i = 0; i < 3; i++) {
            page.selectRandomRegion();
            page.selectRandomOperator();

            page.writeInetValue("10");
            page.enterMyCallsInYourRegion("10");
            page.enterMyCountSms("10");

            ResultPage ResultPage = page.pressSubmitButton();

            $$(By.xpath("//a[text()='Посмотреть подробно']")).shouldHave(size(10));

            ResultPage.clickBackToCalculatorPageLink();
        }
    }

    @Test
    public void TestCheckingTheSearchResultWhenOneInternetFieldIsFilled() {
        CalculatorPage page = open("https://tarifer.ru/calculator", CalculatorPage.class);
        page.selectRandomRegion();
        page.selectRandomOperator();

        page.enterInet("200");

        ResultPage ResultPage = page.pressSubmitButton();

        ResultPage.selectRandomTariffInSearchResult();

        int expectedValueInet = Integer.parseInt($(By.xpath("//tbody/tr/td[text()='Интернет']/../td[3]")).text());

        Assert.assertTrue(expectedValueInet >= 200 * 30,
                "Сорян, у выбранного тарифа мегобайтов меньше, чем ожидалось");
    }

    @Test
    public void TestCheckCurrentVisibilityNameTariff() {
        CalculatorPage page = open("https://tarifer.ru/calculator", CalculatorPage.class);
        page.selectRandomRegion();
        page.selectRandomOperator();

        page.enterInet("100");
        page.enterMyLongDistanceCalls("10");
        page.enterMyLongDistanceCallsTime("30");

        ResultPage ResultPage = page.pressSubmitButton();

        Random rnd = new Random(System.currentTimeMillis());
        int numberTariffInTable = rnd.nextInt(10);

        String nameOperator = ResultPage.getOperatorTariff(numberTariffInTable);
        String nameTariff = ResultPage.getNameTariff(numberTariffInTable);

        ResultPage.selectTariffInSearchResult(numberTariffInTable);

        $(By.tagName("h1")).shouldHave(text("Тариф " + nameOperator + " - " + nameTariff));

        $(By.xpath("//ol[@class='breadcrumb']/li[6]/a")).shouldHave((text(nameTariff)));
        $(By.xpath("//ol[@class='breadcrumb']/li[5]/a")).shouldHave((text(nameOperator)));
    }

    @Test
    public void TestCheckingTheSearchResultWhenOneSMSFieldIsFilled() {
        CalculatorPage page = open("https://tarifer.ru/calculator", CalculatorPage.class);
        page.selectRandomRegion();
        page.selectRandomOperator();

        page.enterMyCountSms("100");

        ResultPage ResultPage = page.pressSubmitButton();

        ResultPage.selectRandomTariffInSearchResult();

        int expectedValueInet = Integer.parseInt($(By.xpath("//tbody/tr/td[text()='Исходящее SMS']/../td[3]")).text());

        Assert.assertTrue(expectedValueInet >= 100 * 30,
                "Сорян, у выбранного тарифа количество смс меньше, чем мне нужно");
    }

    @Test
    public void TestCheckAllOperatorsInTheSearchMatchTheSelected() {
        CalculatorPage page = open("https://tarifer.ru/calculator", CalculatorPage.class);
        page.selectRandomRegion();

        $$(By.className("select2-selection__arrow")).get(1).click();
        $$(By.xpath("//span[@class='select2-results']/ul/li[contains(@id, select2-operator-9g-result)]"))
                .get(1)
                .click();

        page.enterMyCountSms("100");
        page.enterOtherCountSms("100");
        page.enterInet("123");

        page.enterMyLongDistanceCalls("10");
        page.enterMyLongDistanceCallsTime("60");
        page.enterMyCallsInYourRegion("10");
        page.enterMyCallsInYourRegionTime("60");

        page.enterOtherLongDistanceCalls("10");
        page.enterOtherLongDistanceCallsTime("60");
        page.enterOtherCallsInYourRegion("10");
        page.enterOtherCallsInYourRegionTime("60");

        ResultPage ResultPage = page.pressSubmitButton();

        for (int i = 0; i < 10; i++) {
            String name = ResultPage.getOperatorTariff(i);
            Assert.assertEquals(name,"Билайн");
        }
    }
}
