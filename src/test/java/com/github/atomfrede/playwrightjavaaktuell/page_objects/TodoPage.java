package com.github.atomfrede.playwrightjavaaktuell.page_objects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.assertj.core.api.Assertions;

public class TodoPage {

    private String baseUrl;
    private Page page;
    private Locator pageHeading;
    private Locator titleInput;
    private Locator todoListItems;

    public TodoPage(Page page, String baseUrl) {
        this.baseUrl = baseUrl;
        this.page = page;

        pageHeading = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("todos"));
        titleInput = page.getByPlaceholder("What needs to be done?");
        todoListItems = page.locator(".todo-list");
    }

    public void navigate() {
        page.navigate(baseUrl);

        PlaywrightAssertions.assertThat(pageHeading).isVisible();
    }

    public void createNewTodo(String title) {
        titleInput.fill(title);
        titleInput.press("Enter");

        PlaywrightAssertions.assertThat(todoListItems).isVisible();

        Assertions.assertThat(todoListItems.locator(".view").first().innerText()).isEqualTo(title);

    }
}
