package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.datatable.DataTable;
import java.util.Map;

public class RegistrationSteps {

	@Given("^User is on Registration Page$")
	public void user_is_on_registration_page() {
		System.out.println("User is on Registration Page");
		// WebDriver code to navigate to registration page
	}

	@When("^User enters valid registration details$")
	public void user_enters_valid_registration_details(DataTable dataTable) {
		Map<String, String> registrationData = dataTable.asMap(String.class, String.class);
		System.out.println("Entering registration details:");
		for (Map.Entry<String, String> entry : registrationData.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		// WebDriver code to enter registration details
	}

	@When("^User enters registration details with existing email$")
	public void user_enters_registration_details_with_existing_email(DataTable dataTable) {
		Map<String, String> registrationData = dataTable.asMap(String.class, String.class);
		System.out.println("Entering registration details with existing email:");
		for (Map.Entry<String, String> entry : registrationData.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		// WebDriver code to enter registration details with existing email
	}

	@When("^User enters registration details with weak password$")
	public void user_enters_registration_details_with_weak_password(DataTable dataTable) {
		Map<String, String> registrationData = dataTable.asMap(String.class, String.class);
		System.out.println("Entering registration details with weak password:");
		for (Map.Entry<String, String> entry : registrationData.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		// WebDriver code to enter registration details with weak password
	}

	@When("^User enters registration details with mismatched passwords$")
	public void user_enters_registration_details_with_mismatched_passwords(DataTable dataTable) {
		Map<String, String> registrationData = dataTable.asMap(String.class, String.class);
		System.out.println("Entering registration details with mismatched passwords:");
		for (Map.Entry<String, String> entry : registrationData.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		// WebDriver code to enter registration details with mismatched passwords
	}

	@When("^User enters \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
	public void user_enters_registration_fields(String firstName, String lastName, String email, String username, String password) {
		System.out.println("Entering registration fields:");
		System.out.println("First Name: " + firstName);
		System.out.println("Last Name: " + lastName);
		System.out.println("Email: " + email);
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
		// WebDriver code to enter individual registration fields
	}

	@And("^User accepts terms and conditions$")
	public void user_accepts_terms_and_conditions() {
		System.out.println("Accepting terms and conditions");
		// WebDriver code to check terms and conditions checkbox
	}

	@And("^User clicks on Register Button$")
	public void user_clicks_on_register_button() {
		System.out.println("Clicking on Register Button");
		// WebDriver code to click register button
	}

	@Then("^User should be redirected to welcome page$")
	public void user_should_be_redirected_to_welcome_page() {
		System.out.println("User redirected to welcome page");
		// WebDriver code to verify welcome page
	}

	@And("^User should see success message \"([^\"]*)\"$")
	public void user_should_see_success_message(String successMessage) {
		System.out.println("Verifying success message: " + successMessage);
		// WebDriver code to verify success message
	}

	@And("^Confirmation email should be sent to user$")
	public void confirmation_email_should_be_sent_to_user() {
		System.out.println("Verifying confirmation email is sent");
		// Code to verify email is sent (could be API call or database check)
	}

	@And("^User should remain on registration page$")
	public void user_should_remain_on_registration_page() {
		System.out.println("Verifying user remains on registration page");
		// WebDriver code to verify still on registration page
	}

}
