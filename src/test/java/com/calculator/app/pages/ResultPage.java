package com.calculator.app.pages;

import org.openqa.selenium.By;

import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ResultPage {

    public void selectRandomTariffInSearchResult(){
        Random rnd = new Random(System.currentTimeMillis());
        $$(By.xpath("//a[text()='Посмотреть подробно']"))
                .get(rnd.nextInt(10))
                .click();
    }

    public void selectTariffInSearchResult(int numberTariffInTable){
        $$(By.xpath("//a[text()='Посмотреть подробно']"))
                .get(numberTariffInTable)
                .click();
    }

    public String getNameTariff(int numberTariffInTable){
        return $$(By.xpath("//tbody/tr/td[2]")).get(numberTariffInTable).text();
    }

    public String getOperatorTariff(int numberTariffInTable){
        return $$(By.xpath("//tbody/tr/td[1]")).get(numberTariffInTable).text();
    }

    public void clickBackToCalculatorPageLink(){
        $(By.linkText("Калькулятор тарифов")).click();
    }
}
