import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputFormSubmitTest {

    @Test
    public void validateInputFormSubmitFlow() {

        Playwright playwright = Playwright.create();

        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false));

        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        // 1. Open Selenium Playground
        page.navigate("https://www.testmuai.com/selenium-playground/");
        page.getByText("Input Form Submit").click();

        // 2. Click Submit without filling form
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();

        // 3. Assert validation message (browser native required field message OR UI message)
        Locator nameField = page.locator("#name");
        String validationMessage = nameField.evaluate("el => el.validationMessage").toString();

        assertTrue(validationMessage != null && !validationMessage.isEmpty(),
                "Validation message is not displayed for empty submission");

        System.out.println("Validation message shown: " + validationMessage);

        // 4. Fill Name, Email and other fields
        page.locator("#name").fill("John Doe");
        page.locator("#inputEmail4").fill("john.doe@test.com");
        page.locator("#inputPassword4").fill("Test@123");
        page.locator("#company").fill("TestMu AI");
        page.locator("#websitename").fill("https://testmuai.com");
        page.locator("#inputCity").fill("Plano");
        page.locator("#inputAddress1").fill("123 Main Street");
        page.locator("#inputAddress2").fill("Suite 100");
        page.locator("#inputState").fill("Texas");
        page.locator("#inputZip").fill("75024");

        // 5. Select Country from dropdown using text
        page.selectOption("#country", new SelectOption().setLabel("United States"));

        // 6. Click Submit after filling form
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();

        // 7. Validate success message
        Locator successMsg = page.locator(".success-msg, .alert, p, h1, h2")
                .filter(new Locator.FilterOptions().setHasText(
                        "Thanks for contacting us, we will get back to you shortly."));

        assertTrue(successMsg.first().isVisible(),
                "Success message not displayed");

        String actualText = successMsg.first().textContent().trim();

        assertEquals(
                "Thanks for contacting us, we will get back to you shortly.",
                actualText
        );

        System.out.println("Form submitted successfully and message validated.");

        browser.close();
        playwright.close();
    }
}
