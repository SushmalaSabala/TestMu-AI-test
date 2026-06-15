import com.microsoft.playwright.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestScenario2 {

    @Test
    public void validateSliderValue() {

        Playwright playwright = Playwright.create();

        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false));

        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        // Step 1: Open Selenium Playground
        page.navigate("https://www.testmuai.com/selenium-playground/");

        // Click "Drag & Drop Sliders"
        page.getByText("Drag & Drop Sliders").click();

        // Step 2: Locate the slider with default value 15
        Locator slider = page.locator("input[value='15']").first();

        // Move the slider to 95
        page.evaluate(
            "(element) => element.value = 95",
            slider.elementHandle()
        );

        // Trigger input/change events so the UI updates
        page.evaluate(
            "(element) => {" +
            "element.dispatchEvent(new Event('input', { bubbles: true }));" +
            "element.dispatchEvent(new Event('change', { bubbles: true }));" +
            "}",
            slider.elementHandle()
        );

        // Validate the displayed range value
        Locator rangeValue = page.locator("#rangeSuccess");

        assertEquals("95", rangeValue.textContent().trim());

        System.out.println("Slider successfully moved to 95.");

        browser.close();
        playwright.close();
    }
}
```
