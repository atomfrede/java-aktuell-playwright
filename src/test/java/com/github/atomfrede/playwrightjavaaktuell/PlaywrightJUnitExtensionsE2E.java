package com.github.atomfrede.playwrightjavaaktuell;


import com.github.atomfrede.playwrightjavaaktuell.page_objects.LoginPage;
import com.github.atomfrede.playwrightjavaaktuell.page_objects.TodoPage;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.nio.file.Paths;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@UsePlaywright(options = DefaultPlaywrightOptions.class)
public class PlaywrightJUnitExtensionsE2E {

    @LocalServerPort
    private int port;

    private String baseUrl() {
        return "localhost:%s".formatted(port);
    }
    @BeforeEach
    void beforeEach(BrowserContext context, Page page) {

        new LoginPage(page, baseUrl()).doLogin("user", "password");
        // Save storage state into the file.
        context.storageState(new BrowserContext.StorageStateOptions()
                .setPath(Paths.get("build/extension/state.json")));
    }

    @Test
    void smokeTest(Page page) {

        TodoPage todoPage = new TodoPage(page, baseUrl());
        todoPage.navigate();
    }


}
