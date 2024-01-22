package com.github.atomfrede.playwrightjavaaktuell;

import com.microsoft.playwright.junit.Options;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultPlaywrightOptions extends Options {
    @Override
    public String getBrowserName() {
        return "firefox";
    }

    @Override
    public Boolean isHeadless() {
        return true;
    }
}
