package com.github.atomfrede.playwrightjavaaktuell;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Paths;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PlaywrightOhneTestRunner {

    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create(); Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true))) {
            BrowserContext context = browser.newContext();

            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));

            Page page = context.newPage();

            page.navigate("https://www.ijug.eu/de/home/");

            assertThat(page).hasTitle(Pattern.compile("Home: iJUG"));

            page.locator("#nav-primary").getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Java aktuell")).click();

            assertThat(page).hasTitle("Java aktuell: iJUG");

            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("build/ohneRunner/trace.zip")));
        }
    }
}
