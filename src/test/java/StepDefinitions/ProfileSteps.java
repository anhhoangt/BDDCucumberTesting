package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.datatable.DataTable;
import java.util.Map;
import java.util.List;

public class ProfileSteps {

	@Given("^User is logged into the application$")
	public void user_is_logged_into_the_application() {
		System.out.println("User is logged into the application");
		// WebDriver code to ensure user is logged in
	}

	@And("^User is on Dashboard page$")
	public void user_is_on_dashboard_page() {
		System.out.println("User is on Dashboard page");
		// WebDriver code to navigate to dashboard
	}

	@Given("^User is on profile page$")
	public void user_is_on_profile_page() {
		System.out.println("User is on profile page");
		// WebDriver code to navigate to profile page
	}

	@When("^User clicks on Profile menu$")
	public void user_clicks_on_profile_menu() {
		System.out.println("Clicking on Profile menu");
		// WebDriver code to click profile menu
	}

	@Then("^User should be redirected to profile page$")
	public void user_should_be_redirected_to_profile_page() {
		System.out.println("User redirected to profile page");
		// WebDriver code to verify profile page
	}

	@And("^User should see their profile information$")
	public void user_should_see_their_profile_information(DataTable dataTable) {
		List<Map<String, String>> profileData = dataTable.asMaps(String.class, String.class);
		System.out.println("Verifying profile information:");
		for (Map<String, String> row : profileData) {
			System.out.println(row.get("Field") + ": " + row.get("Value"));
		}
		// WebDriver code to verify profile information
	}

	@When("^User updates profile information$")
	public void user_updates_profile_information(DataTable dataTable) {
		Map<String, String> updateData = dataTable.asMap(String.class, String.class);
		System.out.println("Updating profile information:");
		for (Map.Entry<String, String> entry : updateData.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		// WebDriver code to update profile information
	}

	@And("^User clicks on Save Changes button$")
	public void user_clicks_on_save_changes_button() {
		System.out.println("Clicking on Save Changes button");
		// WebDriver code to click save changes button
	}

	@And("^Updated information should be displayed$")
	public void updated_information_should_be_displayed() {
		System.out.println("Verifying updated information is displayed");
		// WebDriver code to verify updated information
	}

	@When("^User clicks on Change Password tab$")
	public void user_clicks_on_change_password_tab() {
		System.out.println("Clicking on Change Password tab");
		// WebDriver code to click change password tab
	}

	@And("^User enters current password \"([^\"]*)\"$")
	public void user_enters_current_password(String currentPassword) {
		System.out.println("Entering current password: " + currentPassword);
		// WebDriver code to enter current password
	}

	@And("^User enters new password \"([^\"]*)\"$")
	public void user_enters_new_password(String newPassword) {
		System.out.println("Entering new password: " + newPassword);
		// WebDriver code to enter new password
	}

	@And("^User confirms new password \"([^\"]*)\"$")
	public void user_confirms_new_password(String confirmPassword) {
		System.out.println("Confirming new password: " + confirmPassword);
		// WebDriver code to confirm new password
	}

	@And("^User clicks on Update Password button$")
	public void user_clicks_on_update_password_button() {
		System.out.println("Clicking on Update Password button");
		// WebDriver code to click update password button
	}

	@And("^User should be able to login with new password$")
	public void user_should_be_able_to_login_with_new_password() {
		System.out.println("Verifying user can login with new password");
		// WebDriver code to test login with new password
	}

	@When("^User clicks on profile picture$")
	public void user_clicks_on_profile_picture() {
		System.out.println("Clicking on profile picture");
		// WebDriver code to click profile picture
	}

	@And("^User selects image file \"([^\"]*)\"$")
	public void user_selects_image_file(String fileName) {
		System.out.println("Selecting image file: " + fileName);
		// WebDriver code to select image file
	}

	@And("^User clicks on Upload button$")
	public void user_clicks_on_upload_button() {
		System.out.println("Clicking on Upload button");
		// WebDriver code to click upload button
	}

	@And("^New profile picture should be displayed$")
	public void new_profile_picture_should_be_displayed() {
		System.out.println("Verifying new profile picture is displayed");
		// WebDriver code to verify new profile picture
	}

	@When("^User clicks on Account Settings tab$")
	public void user_clicks_on_account_settings_tab() {
		System.out.println("Clicking on Account Settings tab");
		// WebDriver code to click account settings tab
	}

	@And("^User clicks on Delete Account button$")
	public void user_clicks_on_delete_account_button() {
		System.out.println("Clicking on Delete Account button");
		// WebDriver code to click delete account button
	}

	@And("^User confirms account deletion$")
	public void user_confirms_account_deletion() {
		System.out.println("Confirming account deletion");
		// WebDriver code to confirm account deletion
	}

	@And("^User enters password for confirmation$")
	public void user_enters_password_for_confirmation() {
		System.out.println("Entering password for confirmation");
		// WebDriver code to enter password for confirmation
	}

	@And("^User should be logged out$")
	public void user_should_be_logged_out() {
		System.out.println("Verifying user is logged out");
		// WebDriver code to verify logout
	}

	@And("^User should receive account deletion email$")
	public void user_should_receive_account_deletion_email() {
		System.out.println("Verifying account deletion email is sent");
		// Code to verify email is sent
	}

	@When("^User updates email address to \"([^\"]*)\"$")
	public void user_updates_email_address_to(String newEmail) {
		System.out.println("Updating email address to: " + newEmail);
		// WebDriver code to update email address
	}

	@Then("^User should see message \"([^\"]*)\"$")
	public void user_should_see_message(String message) {
		System.out.println("Verifying message: " + message);
		// WebDriver code to verify message
	}

	@And("^User should verify new email address$")
	public void user_should_verify_new_email_address() {
		System.out.println("User should verify new email address");
		// Code to handle email verification process
	}

	@When("^User clicks verification link in email$")
	public void user_clicks_verification_link_in_email() {
		System.out.println("Clicking verification link in email");
		// Code to simulate clicking verification link
	}

	@Then("^Email address should be updated$")
	public void email_address_should_be_updated() {
		System.out.println("Verifying email address is updated");
		// WebDriver code to verify email update
	}

	@When("^User clicks on Privacy Settings tab$")
	public void user_clicks_on_privacy_settings_tab() {
		System.out.println("Clicking on Privacy Settings tab");
		// WebDriver code to click privacy settings tab
	}

	@And("^User updates privacy settings$")
	public void user_updates_privacy_settings(DataTable dataTable) {
		List<Map<String, String>> privacySettings = dataTable.asMaps(String.class, String.class);
		System.out.println("Updating privacy settings:");
		for (Map<String, String> setting : privacySettings) {
			System.out.println(setting.get("Setting") + ": " + setting.get("Value"));
		}
		// WebDriver code to update privacy settings
	}

	@And("^User clicks on Save Settings button$")
	public void user_clicks_on_save_settings_button() {
		System.out.println("Clicking on Save Settings button");
		// WebDriver code to click save settings button
	}

	@And("^Settings should be applied to user profile$")
	public void settings_should_be_applied_to_user_profile() {
		System.out.println("Verifying settings are applied to user profile");
		// WebDriver code to verify settings are applied
	}

}
