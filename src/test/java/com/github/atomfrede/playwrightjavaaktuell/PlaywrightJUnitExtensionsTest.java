package com.github.atomfrede.playwrightjavaaktuell;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.UsePlaywright;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@UsePlaywright(options = DefaultPlaywrightOptions.class)
public class PlaywrightJUnitExtensionsTest {

    @LocalServerPort
    private int port;

    @Test
    void smokeTest(Page page) {

        page.navigate("localhost:"+port);

        Locator username = page.getByPlaceholder("Username");
        Locator password = page.getByPlaceholder("Password");

        PlaywrightAssertions.assertThat(username).isVisible();
        PlaywrightAssertions.assertThat(password).isVisible();
        
    }
}
