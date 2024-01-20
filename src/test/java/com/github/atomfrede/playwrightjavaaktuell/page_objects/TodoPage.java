package com.github.atomfrede.playwrightjavaaktuell.page_objects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

public class TodoPage {

    private String baseUrl;
    private Page page;
    private Locator pageHeading;

    public TodoPage(Page page, String baseUrl) {
        this.baseUrl = baseUrl;
        this.page = page;

        pageHeading = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("todos"));
    }

    public void navigate() {
        page.navigate(baseUrl);

        PlaywrightAssertions.assertThat(pageHeading).isVisible();
    }
}
