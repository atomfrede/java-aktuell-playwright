package com.github.atomfrede.playwrightjavaaktuell;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlaywrightJUnitTestConfiguration {

    Playwright playwright;
    Browser browser;

    @BeforeAll
    void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterAll
    void closeBrowser() {
        playwright.close();
    }

    BrowserContext context;
    Page page;

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
    }

    @AfterEach
    void closeContext(TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();;

        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("build/ohneRunner/" + displayName +".zip")));
        context.close();
    }
}
