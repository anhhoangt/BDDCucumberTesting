package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

public class LoginSteps {

	@Given("^User is on Application Home Page$")
	public void user_application_home_page() {
		System.out.println("User is on Application Home Page");
		// WebDriver code to navigate to home page
	}

	@When("^Application Page Title is FREE CRM$")
	public void application_page_title_is_free_crm() {
		System.out.println("Verifying application page title is FREE CRM");
		// WebDriver code to verify page title
	}

	@Then("^user enters valid username and password$")
	public void user_enters_valid_username_and_password() {
		System.out.println("Entering valid username and password");
		// WebDriver code to enter valid credentials
	}

	@Then("^user enters invalid username and password$")
	public void user_enters_invalid_username_and_password() {
		System.out.println("Entering invalid username and password");
		// WebDriver code to enter invalid credentials
	}

	@Then("^user enters empty username and valid password$")
	public void user_enters_empty_username_and_valid_password() {
		System.out.println("Entering empty username and valid password");
		// WebDriver code to enter empty username
	}

	@Then("^user enters valid username and empty password$")
	public void user_enters_valid_username_and_empty_password() {
		System.out.println("Entering valid username and empty password");
		// WebDriver code to enter empty password
	}

	@When("^user enters username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void user_enters_username_and_password(String username, String password) {
		System.out.println("Entering username: " + username + " and password: " + password);
		// WebDriver code to enter specific username and password
	}

	@And("^user clicks on Login Button$")
	public void user_clicks_on_login_button() {
		System.out.println("Clicking on Login Button");
		// WebDriver code to click login button
	}

	@Then("^User should be redirected to Dashboard$")
	public void user_should_be_redirected_to_dashboard() {
		System.out.println("User redirected to Dashboard");
		// WebDriver code to verify dashboard page
	}

	@And("^User should see welcome message$")
	public void user_should_see_welcome_message() {
		System.out.println("Verifying welcome message is displayed");
		// WebDriver code to verify welcome message
	}

	@Then("^User should see error message \"([^\"]*)\"$")
	public void user_should_see_error_message(String errorMessage) {
		System.out.println("Verifying error message: " + errorMessage);
		// WebDriver code to verify error message
	}

	@And("^User should remain on login page$")
	public void user_should_remain_on_login_page() {
		System.out.println("Verifying user remains on login page");
		// WebDriver code to verify still on login page
	}

	@When("^User clicks on \"([^\"]*)\" link$")
	public void user_clicks_on_link(String linkText) {
		System.out.println("Clicking on link: " + linkText);
		// WebDriver code to click on specific link
	}

	@Then("^User should be redirected to password reset page$")
	public void user_should_be_redirected_to_password_reset_page() {
		System.out.println("User redirected to password reset page");
		// WebDriver code to verify password reset page
	}

	@When("^User enters valid email address$")
	public void user_enters_valid_email_address() {
		System.out.println("Entering valid email address");
		// WebDriver code to enter email
	}

	@And("^User clicks on \"([^\"]*)\" button$")
	public void user_clicks_on_button(String buttonText) {
		System.out.println("Clicking on button: " + buttonText);
		// WebDriver code to click specific button
	}

	@Then("^User should see confirmation message \"([^\"]*)\"$")
	public void user_should_see_confirmation_message(String confirmationMessage) {
		System.out.println("Verifying confirmation message: " + confirmationMessage);
		// WebDriver code to verify confirmation message
	}

	@And("^User checks \"([^\"]*)\" checkbox$")
	public void user_checks_checkbox(String checkboxText) {
		System.out.println("Checking checkbox: " + checkboxText);
		// WebDriver code to check checkbox
	}

	@When("^User closes browser and reopens application$")
	public void user_closes_browser_and_reopens_application() {
		System.out.println("Closing and reopening browser");
		// WebDriver code to close and reopen browser
	}

	@Then("^User should still be logged in$")
	public void user_should_still_be_logged_in() {
		System.out.println("Verifying user is still logged in");
		// WebDriver code to verify login state
	}

}
