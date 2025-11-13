package com.web.base.pagesteps;

import com.thoughtworks.gauge.Step;
import com.web.base.pages.BaseMasterPage;

public class ButtonClick extends BaseSteps {

    @Step("Login iconuna tikla")
    public void ButtonClick() {
        BaseMasterPage.BTN_Login.click();
    }
}