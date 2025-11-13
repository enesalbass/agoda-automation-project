package com.web.base.pagesteps;

import com.thoughtworks.gauge.Step;
import com.web.base.pages.BaseMasterPage;

public class ReservationSteps extends BaseSteps {

    @Step("Searchbox alanına Antalya yaz ve ilk öneriye tıkla")
    public void writeAntalyaAndClickSuggestion() {
        // 1️⃣ Arama kutusuna tıkla
        BaseMasterPage.BTN_TextInput.click();

        // 2️⃣ Varsa eski değeri temizle
        BaseMasterPage.BTN_TextInput.clearText();

        // 3️⃣ "Antalya" yaz
        BaseMasterPage.BTN_TextInput.type("Antalya");

        // 4️⃣ Öneri listesinin görünmesini bekle (1-2 saniye bekleme)
        BaseMasterPage.BTN_FirstSuggestionAntalya.waitUntilVisible(5);

        // 5️⃣ İlk öneriye tıkla
        BaseMasterPage.BTN_FirstSuggestionAntalya.click();
    }

    @Step("Konaklama tarihlerini seç")
    public void selectStayDates() {
        // Takvimin açıldığından emin olmak için ilk gün label'ını bekle
        BaseMasterPage.BTN_FirstDate.waitUntilVisible(5);

        // Giriş ve çıkış günlerini sırayla seç
        BaseMasterPage.BTN_FirstDate.click();
        BaseMasterPage.BTN_SecondDate.click();
    }


    @Step("2 yetişkin 1 çocuk seç")
    public void setGuests() {
        BaseMasterPage.BTN_BireySec.click();
        BaseMasterPage.BTN_AddChildrien.click();
    }

    @Step("Arama butonuna tıkla")
    public void clickSearchButton() {
        BaseMasterPage.BTN_Search.click();
    }
}
