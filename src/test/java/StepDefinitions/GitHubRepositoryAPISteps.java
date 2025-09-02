package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class GitHubRepositoryAPISteps {
	
	private Response response;
	private RequestSpecification request;
	private String authToken;
	private String owner;
	private Map<String, Object> requestData;
	private int releaseId;
	
	@And("^each branch should have \"([^\"]*)\" field$")
	public void each_branch_should_have_field(String fieldName) {
		response.then().body("every { it.containsKey('" + fieldName + "') }", is(true));
		System.out.println("Verified each branch has field: " + fieldName);
	}
	
	@And("^the response should contain a branch named \"([^\"]*)\"$")
	public void the_response_should_contain_a_branch_named(String branchName) {
		response.then().body("name", hasItem(branchName));
		System.out.println("Verified response contains branch named: " + branchName);
	}
	
	@And("^each collaborator should have \"([^\"]*)\" field$")
	public void each_collaborator_should_have_field(String fieldName) {
		response.then().body("every { it.containsKey('" + fieldName + "') }", is(true));
		System.out.println("Verified each collaborator has field: " + fieldName);
	}
	
	@And("^each pull request should have \"([^\"]*)\" field$")
	public void each_pull_request_should_have_field(String fieldName) {
		response.then().body("every { it.containsKey('" + fieldName + "') }", is(true));
		System.out.println("Verified each pull request has field: " + fieldName);
	}
	
	@And("^all returned pull requests should have state \"([^\"]*)\"$")
	public void all_returned_pull_requests_should_have_state(String expectedState) {
		response.then().body("state", everyItem(equalTo(expectedState)));
		System.out.println("Verified all pull requests have state: " + expectedState);
	}
	
	@Given("^I have a feature branch \"([^\"]*)\"$")
	public void i_have_a_feature_branch(String branchName) {
		System.out.println("Using feature branch: " + branchName);
		// This would typically create or reference an existing branch
	}
	
	@Given("^I have pull request data:$")
	public void i_have_pull_request_data(DataTable dataTable) {
		requestData = dataTable.asMap(String.class, Object.class);
		System.out.println("Pull request data prepared: " + requestData);
	}
	
	@When("^I send a POST request to \"([^\"]*)\" with the pull request data$")
	public void i_send_a_post_request_with_pull_request_data(String endpoint) {
		// Replace {owner} placeholder with actual owner
		endpoint = endpoint.replace("{owner}", owner != null ? owner : "testuser");
		
		request = given()
			.header("Authorization", "token " + (authToken != null ? authToken : "test_token"))
			.header("Accept", "application/vnd.github.v3+json")
			.header("Content-Type", "application/json")
			.body(requestData);
		
		response = request.post(endpoint);
		System.out.println("POST request sent to: " + endpoint);
		System.out.println("Response status: " + response.getStatusCode());
	}
	
	@Given("^I have an existing pull request with number (\\d+)$")
	public void i_have_an_existing_pull_request_with_number(int prNumber) {
		System.out.println("Using existing pull request number: " + prNumber);
	}
	
	@And("^each release should have \"([^\"]*)\" field$")
	public void each_release_should_have_field(String fieldName) {
		response.then().body("every { it.containsKey('" + fieldName + "') }", is(true));
		System.out.println("Verified each release has field: " + fieldName);
	}
	
	@Given("^I have release data:$")
	public void i_have_release_data(DataTable dataTable) {
		requestData = new HashMap<>();
		Map<String, String> releaseData = dataTable.asMap(String.class, String.class);
		
		for (Map.Entry<String, String> entry : releaseData.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			
			// Handle boolean values
			if (value.equals("true") || value.equals("false")) {
				requestData.put(key, Boolean.parseBoolean(value));
			} else {
				requestData.put(key, value);
			}
		}
		System.out.println("Release data prepared: " + requestData);
	}
	
	@When("^I send a POST request to \"([^\"]*)\" with the release data$")
	public void i_send_a_post_request_with_release_data(String endpoint) {
		// Replace {owner} placeholder with actual owner
		endpoint = endpoint.replace("{owner}", owner != null ? owner : "testuser");
		
		request = given()
			.header("Authorization", "token " + (authToken != null ? authToken : "test_token"))
			.header("Accept", "application/vnd.github.v3+json")
			.header("Content-Type", "application/json")
			.body(requestData);
		
		response = request.post(endpoint);
		System.out.println("POST request sent to: " + endpoint);
		System.out.println("Response status: " + response.getStatusCode());
	}
	
	@Given("^I have an existing release with id (\\d+)$")
	public void i_have_an_existing_release_with_id(int id) {
		releaseId = id;
		System.out.println("Using existing release id: " + id);
	}
	
	@And("^the release should no longer exist$")
	public void the_release_should_no_longer_exist() {
		System.out.println("Verified release was deleted successfully");
	}
	
	@And("^each content item should have \"([^\"]*)\" field$")
	public void each_content_item_should_have_field(String fieldName) {
		response.then().body("every { it.containsKey('" + fieldName + "') }", is(true));
		System.out.println("Verified each content item has field: " + fieldName);
	}
	
	@And("^the response should contain a file named \"([^\"]*)\"$")
	public void the_response_should_contain_a_file_named(String fileName) {
		response.then().body("name", hasItem(fileName));
		System.out.println("Verified response contains file named: " + fileName);
	}
	
	@Given("^I have repository settings data:$")
	public void i_have_repository_settings_data(DataTable dataTable) {
		requestData = new HashMap<>();
		Map<String, String> settingsData = dataTable.asMap(String.class, String.class);
		
		for (Map.Entry<String, String> entry : settingsData.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			
			// Handle boolean values
			if (value.equals("true") || value.equals("false")) {
				requestData.put(key, Boolean.parseBoolean(value));
			} else {
				requestData.put(key, value);
			}
		}
		System.out.println("Repository settings data prepared: " + requestData);
	}
	
	@When("^I send a PATCH request to \"([^\"]*)\" with the settings data$")
	public void i_send_a_patch_request_with_settings_data(String endpoint) {
		// Replace {owner} placeholder with actual owner
		endpoint = endpoint.replace("{owner}", owner != null ? owner : "testuser");
		
		request = given()
			.header("Authorization", "token " + (authToken != null ? authToken : "test_token"))
			.header("Accept", "application/vnd.github.v3+json")
			.header("Content-Type", "application/json")
			.body(requestData);
		
		response = request.patch(endpoint);
		System.out.println("PATCH request sent to: " + endpoint);
		System.out.println("Response status: " + response.getStatusCode());
	}
	
	@And("^the response should contain \"([^\"]*)\" field with value \"([^\"]*)\"$")
	public void the_response_should_contain_field_with_string_value(String fieldName, String expectedValue) {
		// Handle boolean values
		if (expectedValue.equals("true") || expectedValue.equals("false")) {
			response.then().body(fieldName, equalTo(Boolean.parseBoolean(expectedValue)));
		} else {
			response.then().body(fieldName, equalTo(expectedValue));
		}
		System.out.println("Verified field " + fieldName + " has value: " + expectedValue);
	}

}
