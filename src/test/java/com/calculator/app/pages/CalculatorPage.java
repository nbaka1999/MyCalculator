package com.calculator.app.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import com.codeborne.selenide.ElementsCollection;

import java.util.Random;

import static com.codeborne.selenide.Selenide.*;

public class CalculatorPage {

    private static final String MAIN_URL = "https://tarifer.ru/calculator";

    String[] Providers = {"МТС", "Билайн", "TELE2", "Мегафон", "Мотив", "Yota"};

    // локаторы для "Связь с абонентами своего оператора"
    private SelenideElement myCallsInYourRegion = $(By.name("qslcall"));
    private SelenideElement myCallsInYourRegionTime = $(By.name("tslcall"));
    private SelenideElement myLongDistanceCalls = $(By.name("qsicall"));
    private SelenideElement myLongDistanceCallsTime = $(By.name("tsicall"));
    private SelenideElement myCountSms = $(By.name("qslsms"));

    // локаторы для "Связь с абонентами других операторов"
    private SelenideElement otherCallsInYourRegion = $(By.name("qolcall"));
    private SelenideElement otherCallsInYourRegionTime = $(By.name("tolcall"));
    private SelenideElement otherLongDistanceCalls = $(By.name("qoicall"));
    private SelenideElement otherLongDistanceCallsTime = $(By.name("toicall"));
    private SelenideElement otherCountSms = $(By.name("qolsms"));

    private SelenideElement inet = $(By.name("inet"));

    public void openCalculatorPage(){
        open(MAIN_URL);
    }

    // методы ввода для блока "Связь с абонентами своего оператора"
    public void enterMyCountSms(String val){
        myCountSms.clear();
        myCountSms.setValue(val);
    }

    public void enterMyCallsInYourRegion(String val){
        myCallsInYourRegion.clear();
        myCallsInYourRegion.setValue(val);
    }

    public void enterMyCallsInYourRegionTime(String val){
        myCallsInYourRegionTime.clear();
        myCallsInYourRegionTime.setValue(val);
    }

    public void enterMyLongDistanceCalls(String val){
        myLongDistanceCalls.clear();
        myLongDistanceCalls.setValue(val);
    }

    public void enterMyLongDistanceCallsTime(String val){
        myLongDistanceCallsTime.clear();
        myLongDistanceCallsTime.setValue(val);
    }

    //методы ввода для блока "Связь с абонентами других операторов"
    public void enterOtherCountSms(String val){
        otherCountSms.clear();
        otherCountSms.setValue(val);
    }

    public void enterOtherCallsInYourRegion(String val){
        otherCallsInYourRegion.clear();
        otherCallsInYourRegion.setValue(val);
    }

    public void enterOtherCallsInYourRegionTime(String val){
        otherCallsInYourRegionTime.clear();
        otherCallsInYourRegionTime.setValue(val);
    }

    public void enterOtherLongDistanceCalls(String val){
        otherLongDistanceCalls.clear();
        otherLongDistanceCalls.setValue(val);
    }

    public void enterOtherLongDistanceCallsTime(String val){
        otherLongDistanceCallsTime.clear();
        otherLongDistanceCallsTime.setValue(val);
    }

    // ввод для интернета
    public void enterInet(String val){
        inet.clear();
        inet.setValue(val);
    }

    public ResultPage pressSubmitButton(){
        executeJavaScript("arguments[0].scrollIntoView(true);", otherLongDistanceCallsTime);
        $(By.className("btn")).click();
        return page(ResultPage.class);
    }

    public void writeInetValue(String value){
        $(By.name("inet")).clear();
        $(By.name("inet")).setValue(value);
    }

    public void selectRandomRegion(){
        $$(By.className("select2-selection__arrow")).get(0).click();
        ElementsCollection elementts = $$(By.xpath(
                "//span[@class='select2-results']/ul/li[contains(@id, select2-region-n9-result)]"));
        Random rnd = new Random(System.currentTimeMillis());
        elementts.get(rnd.nextInt(elementts.size())).click();
        executeJavaScript("window.scrollBy (0, -250)","");
    }

    public void selectRandomOperator(){
        $$(By.className("select2-selection__arrow")).get(1).click();
        ElementsCollection elementts = $$(By.xpath(
                "//span[@class='select2-results']/ul/li[contains(@id, select2-operator-9g-result)]"));
        Random rnd = new Random(System.currentTimeMillis());
        elementts.get(rnd.nextInt(elementts.size())).click();
        executeJavaScript("window.scrollBy (0, -250)","");
    }

    public String getSelectedRegion(){
        return $(By.xpath("//span[contains(@aria-labelledby, select2-region)]/span[contains(@class, select2-region)]")).text();
    }
}
