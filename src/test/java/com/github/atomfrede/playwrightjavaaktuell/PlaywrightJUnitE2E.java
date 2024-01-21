package com.github.atomfrede.playwrightjavaaktuell;

import com.github.atomfrede.playwrightjavaaktuell.page_objects.LoginPage;
import com.github.atomfrede.playwrightjavaaktuell.page_objects.TodoPage;
import com.microsoft.playwright.BrowserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.nio.file.Paths;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlaywrightJUnitE2E extends PlaywrightJUnitTestConfiguration {

    @LocalServerPort
    private int port;

    private String baseUrl() {
        return "localhost:%s".formatted(port);
    }

    @BeforeEach
    void beforeEach() {
        new LoginPage(page, baseUrl()).doLogin("user", "password");
        // Save storage state into the file.
        context.storageState(new BrowserContext.StorageStateOptions()
                .setPath(Paths.get("build/junit/state.json")));

    }

    @Test
    void createNewTodo() {
        TodoPage todoPage = new TodoPage(page, baseUrl());
        todoPage.navigate();

        todoPage.createNewTodo("Write article");
    }

    @Test
    void smokeTest() {

        TodoPage todoPage = new TodoPage(page, baseUrl());
        todoPage.navigate();
    }

}
