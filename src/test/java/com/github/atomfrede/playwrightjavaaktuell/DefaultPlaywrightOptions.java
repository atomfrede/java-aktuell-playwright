package com.github.atomfrede.playwrightjavaaktuell;

import com.microsoft.playwright.junit.Options;

public class DefaultPlaywrightOptions extends Options {
    @Override
    public String getBrowserName() {
        return "firefox";
    }

    @Override
    public Boolean isHeadless() {
        return false;
    }
}
