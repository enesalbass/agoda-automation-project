package com.web.base.pageElement;

import com.web.base.backend.WebAutomationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class BeinTextBox extends PageElementModel {
    private static final Log log = LogFactory.getLog(BeinTextBox.class);

    public BeinTextBox(selectorNames selectorName, String selectorValue) {
        super(selectorName, selectorValue);
    }

    /** Yardımcı: güvenli element çözümleyici */
    private WebElement resolve() {
        try {
            return getWebElement();
        } catch (NoSuchElementException e){
            String error = "ELEMENT NOT FOUND: " + getLoggingName();
            log.error(error);
            throw new WebAutomationException(error);
        } catch (Exception e){
            String error = "ELEMENT RESOLVE ERROR: " + getLoggingName() + " -> " + e.getMessage();
            log.error(error, e);
            throw new WebAutomationException(error);
        }
    }

    /** Basit tıklama (odak almak için faydalı) */
    public void click() {
        log.info("CLICK TEXTBOX " + getLoggingName());
        try {
            resolve().click();
        } catch (Exception e){
            String error = "COULD NOT CLICK TEXTBOX: " + getLoggingName();
            log.error(error, e);
            throw new WebAutomationException(error);
        }
    }

    /** Eski kullanım ile uyumlu yazma */
    public void type(String inputText){
        log.info("TYPE TEXTBOX " + getLoggingName() + " TEXT: " + inputText);
        WebElement we = resolve();
        try {
            we.sendKeys(inputText);
        } catch (Exception e) {
            String error = "COULD NOT TYPE TEXTBOX: " + getLoggingName() + " TEXT IS: " + inputText;
            log.error(error, e);
            throw new WebAutomationException(error);
        }
    }

    /** Genel amaçlı sendKeys (CharSequence…) */
    public void sendKeys(CharSequence... keys) {
        log.info("SEND KEYS TO TEXTBOX " + getLoggingName());
        WebElement we = resolve();
        try {
            we.sendKeys(keys);
        } catch (Exception e) {
            String error = "COULD NOT SEND KEYS TO TEXTBOX: " + getLoggingName();
            log.error(error, e);
            throw new WebAutomationException(error);
        }
    }

    /** ENTER tuşu */
    public void sendEnter() {
        sendKeys(Keys.ENTER);
    }

    /** CTRL+A + DEL ile temizle (çoğu durumda daha güvenli) */
    public void clearWithCtrlA() {
        log.info("CLEAR (CTRL+A) TEXTBOX " + getLoggingName());
        WebElement we = resolve();
        try {
            we.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            we.sendKeys(Keys.DELETE);
        } catch (Exception e){
            String error = "COULD NOT CTRL+A CLEAR TEXTBOX: " + getLoggingName();
            log.error(error, e);
            throw new WebAutomationException(error);
        }
    }

    /** Selenium clear() */
    public void clearText(){
        log.info("CLEAR TEXTBOX " + getLoggingName());
        WebElement we = resolve();
        try {
            we.clear();
        } catch (Exception e){
            String error = "COULD NOT CLEAR TEXTBOX: " + getLoggingName();
            log.error(error, e);
            throw new WebAutomationException(error);
        }
    }

    /** Görünür olana kadar bekleyip yaz */
    public void waitUntilVisibleAndType(String inputText, int... timeOut){
        log.info("WAIT THEN TYPE TEXTBOX: " + getLoggingName());
        waitUntilVisible(timeOut);
        // bazı sayfalarda önce odak aldırmak daha stabil olur
        try { click(); } catch (Exception ignored) {}
        clearWithCtrlA();
        type(inputText);
    }

    /** Değeri value attribute’undan al */
    public String getValue() {
        try {
            String val = resolve().getAttribute("value");
            log.info("GET VALUE TEXTBOX " + getLoggingName() + " => " + val);
            return val;
        } catch (Exception e){
            String error = "COULD NOT GET VALUE FROM TEXTBOX: " + getLoggingName();
            log.error(error, e);
            throw new WebAutomationException(error);
        }
    }

    /** JS ile değer set etme (çok dinamik inputlar için alternatif) */
    public void setValueJS(String value) {
        log.info("SET VALUE (JS) TEXTBOX " + getLoggingName() + " => " + value);
        try {
            WebElement we = resolve();
            ((JavascriptExecutor) getDriver()).executeScript(
                    "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
                    we, value
            );
        } catch (Exception e){
            String error = "COULD NOT SET VALUE (JS) TEXTBOX: " + getLoggingName();
            log.error(error, e);
            throw new WebAutomationException(error);
        }
    }

    /** Etkin mi? */
    public boolean isEnabled() {
        try {
            return resolve().isEnabled();
        } catch (Exception e){
            log.warn("TEXTBOX isEnabled check failed: " + getLoggingName(), e);
            return false;
        }
    }
}
