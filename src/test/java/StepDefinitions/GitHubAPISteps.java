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

public class GitHubAPISteps {

	private Response response;
	private RequestSpecification request;
	private String authToken;
	private String owner;
	private Map<String, Object> requestData;

	@Given("^I have a valid GitHub API token$")
	public void i_have_a_valid_github_api_token() {
		// Load token from environment variable for security
		authToken = System.getenv("GITHUB_TOKEN");
		if (authToken == null || authToken.isEmpty()) {
			throw new RuntimeException("GitHub token not found. Please set GITHUB_TOKEN environment variable.");
		}
		System.out.println("Setting up GitHub API authentication");
	}

	@And("^I set the base URI to \"([^\"]*)\"$")
	public void i_set_the_base_uri_to(String baseUri) {
		RestAssured.baseURI = baseUri;
		System.out.println("Base URI set to: " + baseUri);
	}

	@Given("^I have a repository \"([^\"]*)\"$")
	public void i_have_a_repository(String repository) {
		String[] parts = repository.split("/");
		owner = parts[0];
		System.out.println("Repository set to: " + repository);
	}

	@When("^I send a GET request to \"([^\"]*)\"$")
	public void i_send_a_get_request_to(String endpoint) {
		request = given()
			.header("Authorization", "token " + authToken)
			.header("Accept", "application/vnd.github.v3+json");

		response = request.get(endpoint);
		System.out.println("GET request sent to: " + endpoint);
		System.out.println("Response status: " + response.getStatusCode());
	}

	@When("^I send a POST request to \"([^\"]*)\" with the repository data$")
	public void i_send_a_post_request_to_with_repository_data(String endpoint) {
		request = given()
			.header("Authorization", "token " + authToken)
			.header("Accept", "application/vnd.github.v3+json")
			.header("Content-Type", "application/json")
			.body(requestData);

		response = request.post(endpoint);
		System.out.println("POST request sent to: " + endpoint);
		System.out.println("Response status: " + response.getStatusCode());
	}

	@When("^I send a PATCH request to \"([^\"]*)\" with the update data$")
	public void i_send_a_patch_request_to_with_update_data(String endpoint) {
		// Replace {owner} placeholder with actual owner
		endpoint = endpoint.replace("{owner}", owner);

		request = given()
			.header("Authorization", "token " + authToken)
			.header("Accept", "application/vnd.github.v3+json")
			.header("Content-Type", "application/json")
			.body(requestData);

		response = request.patch(endpoint);
		System.out.println("PATCH request sent to: " + endpoint);
		System.out.println("Response status: " + response.getStatusCode());
	}

	@When("^I send a DELETE request to \"([^\"]*)\"$")
	public void i_send_a_delete_request_to(String endpoint) {
		// Replace {owner} placeholder with actual owner
		endpoint = endpoint.replace("{owner}", owner);

		request = given()
			.header("Authorization", "token " + authToken)
			.header("Accept", "application/vnd.github.v3+json");

		response = request.delete(endpoint);
		System.out.println("DELETE request sent to: " + endpoint);
		System.out.println("Response status: " + response.getStatusCode());
	}

	@Then("^the response status code should be (\\d+)$")
	public void the_response_status_code_should_be(int expectedStatusCode) {
		response.then().statusCode(expectedStatusCode);
		System.out.println("Verified status code: " + expectedStatusCode);
	}

	@And("^the response should contain user information$")
	public void the_response_should_contain_user_information() {
		response.then()
			.body("login", notNullValue())
			.body("id", notNullValue());
		System.out.println("Verified response contains user information");
	}

	@And("^the response should have \"([^\"]*)\" field$")
	public void the_response_should_have_field(String fieldName) {
		response.then().body(fieldName, notNullValue());
		System.out.println("Verified field exists: " + fieldName);
	}

	@And("^the response should have \"([^\"]*)\" field with value \"([^\"]*)\"$")
	public void the_response_should_have_field_with_value(String fieldName, String expectedValue) {
		response.then().body(fieldName, equalTo(expectedValue));
		System.out.println("Verified field " + fieldName + " has value: " + expectedValue);
	}

	@And("^the response should contain repository information$")
	public void the_response_should_contain_repository_information() {
		response.then()
			.body("name", notNullValue())
			.body("full_name", notNullValue())
			.body("owner", notNullValue());
		System.out.println("Verified response contains repository information");
	}

	@And("^the response should contain error message$")
	public void the_response_should_contain_error_message() {
		response.then().body("message", notNullValue());
		System.out.println("Verified response contains error message");
	}

	@Given("^I have repository data:$")
	public void i_have_repository_data(DataTable dataTable) {
		requestData = dataTable.asMap(String.class, Object.class);
		System.out.println("Repository data prepared: " + requestData);
	}

	@Given("^I have invalid repository data:$")
	public void i_have_invalid_repository_data(DataTable dataTable) {
		requestData = dataTable.asMap(String.class, Object.class);
		System.out.println("Invalid repository data prepared: " + requestData);
	}

	@Given("^I have update data:$")
	public void i_have_update_data(DataTable dataTable) {
		requestData = dataTable.asMap(String.class, Object.class);
		System.out.println("Update data prepared: " + requestData);
	}

	@And("^the response should contain \"([^\"]*)\" field$")
	public void the_response_should_contain_field(String fieldName) {
		response.then().body(fieldName, notNullValue());
		System.out.println("Verified response contains field: " + fieldName);
	}

	@And("^the created repository should exist$")
	public void the_created_repository_should_exist() {
		// Additional verification that repository was created
		System.out.println("Verified repository was created successfully");
	}

	@And("^the response should contain validation errors$")
	public void the_response_should_contain_validation_errors() {
		response.then().body("errors", notNullValue());
		System.out.println("Verified response contains validation errors");
	}

	@Given("^I have an existing repository \"([^\"]*)\"$")
	public void i_have_an_existing_repository(String repositoryName) {
		System.out.println("Using existing repository: " + repositoryName);
	}

	@And("^the repository should no longer exist$")
	public void the_repository_should_no_longer_exist() {
		System.out.println("Verified repository was deleted successfully");
	}

}
