import com.microsoft.playwright.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestScenario1 {

    @Test
    public void validateSimpleFormDemo() {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false));

        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        // Step 1: Open Selenium Playground
        page.navigate("https://www.testmuai.com/selenium-playground/");

        // Step 2: Click "Simple Form Demo"
        page.getByText("Simple Form Demo").click();

        // Step 3: Validate URL contains "simple-form-demo"
        assertTrue(page.url().contains("simple-form-demo"),
                "URL does not contain 'simple-form-demo'");

        // Step 4: Create a string variable
        String message = "Welcome to TestMu AI";

        // Step 5: Enter the message
        page.locator("#user-message").fill(message);

        // Step 6: Click "Get Checked Value"
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Get Checked Value"))
                .click();

        // Step 7: Validate the displayed message
        String displayedMessage = page.locator("#message").textContent().trim();

        assertEquals(message, displayedMessage,
                "Displayed message does not match the entered message");

        System.out.println("Test Passed!");

        browser.close();
        playwright.close();
    }
}
