package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.datatable.DataTable;
import static org.junit.Assert.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class GitHubApiStepsSimple {

    private HttpClient httpClient;
    private HttpResponse<String> response;
    private String baseUri;
    private String authToken;
    private ObjectMapper objectMapper;
    private String repositoryName;
    private Map<String, Object> repositoryData;
    private Map<String, Object> updateData;

    @Given("I have a valid GitHub API token")
    public void i_have_a_valid_github_api_token() {
        // You can set your GitHub token here or use environment variable
        authToken = System.getProperty("github.token");
        if (authToken == null || authToken.isEmpty()) {
            // Check environment variable
            authToken = System.getenv("GITHUB_TOKEN");
        }
        if (authToken == null || authToken.isEmpty()) {
            // For demo purposes - in real scenario, get from environment or config
            authToken = "your_github_token_here";
            System.out.println("WARNING: Using placeholder token. Set GITHUB_TOKEN environment variable or github.token system property for real testing.");
        }

        // Create HTTP client
        httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

        objectMapper = new ObjectMapper();

        System.out.println("Using GitHub token: " + authToken.substring(0, 10) + "...");
    }

    @And("I set the base URI to {string}")
    public void i_set_the_base_uri_to(String uri) {
        baseUri = uri;
        System.out.println("Base URI set to: " + baseUri);
    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String endpoint) {
        try {
            String fullUrl = baseUri + endpoint;
            System.out.println("Sending GET request to: " + fullUrl);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Authorization", "Bearer " + authToken)
                .header("Accept", "application/vnd.github.v3+json")
                .header("User-Agent", "BDD-Cucumber-Testing")
                .GET()
                .build();

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status: " + response.statusCode());
            System.out.println("Response body: " + response.body());

        } catch (Exception e) {
            System.err.println("Error sending request: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to send HTTP request", e);
        }
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        assertEquals("Status code mismatch", expectedStatusCode, response.statusCode());
    }

    @And("the response should contain user information")
    public void the_response_should_contain_user_information() {
        // Verify that the response contains basic user information
        assertNotNull("Response should not be null", response);
        assertNotNull("Response body should not be null", response.body());
        assertFalse("Response body should not be empty", response.body().isEmpty());

        try {
            // Verify JSON structure
            JsonNode jsonNode = objectMapper.readTree(response.body());
            assertTrue("Response should be a JSON object", jsonNode.isObject());
        } catch (Exception e) {
            fail("Response body is not valid JSON: " + e.getMessage());
        }
    }

    @And("the response should have {string} field")
    public void the_response_should_have_field(String fieldName) {
        try {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            assertTrue("Field '" + fieldName + "' should exist", jsonNode.has(fieldName));
            assertNotNull("Field '" + fieldName + "' should not be null", jsonNode.get(fieldName));

            JsonNode fieldValue = jsonNode.get(fieldName);
            if (fieldValue.isTextual()) {
                assertFalse("Field '" + fieldName + "' should not be empty", fieldValue.asText().isEmpty());
            }

            System.out.println("Field '" + fieldName + "': " + fieldValue);

        } catch (Exception e) {
            fail("Error parsing JSON response: " + e.getMessage());
        }
    }

    // Repository-related step definitions
    @Given("I have a repository {string}")
    public void i_have_a_repository(String repoName) {
        this.repositoryName = repoName;
        System.out.println("Testing repository: " + repoName);
    }

    @And("the response should contain repository information")
    public void the_response_should_contain_repository_information() {
        // Verify that the response contains basic repository information
        assertNotNull("Response should not be null", response);
        assertNotNull("Response body should not be null", response.body());
        assertFalse("Response body should not be empty", response.body().isEmpty());

        try {
            // Verify JSON structure and required repository fields
            JsonNode jsonNode = objectMapper.readTree(response.body());
            assertTrue("Response should be a JSON object", jsonNode.isObject());
            assertTrue("Repository should have 'name' field", jsonNode.has("name"));
            assertTrue("Repository should have 'full_name' field", jsonNode.has("full_name"));
            assertTrue("Repository should have 'owner' field", jsonNode.has("owner"));
        } catch (Exception e) {
            fail("Response body is not valid JSON: " + e.getMessage());
        }
    }

    @And("the response should have {string} field with value {string}")
    public void the_response_should_have_field_with_value(String fieldName, String expectedValue) {
        try {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            assertTrue("Field '" + fieldName + "' should exist", jsonNode.has(fieldName));
            JsonNode fieldValue = jsonNode.get(fieldName);
            assertNotNull("Field '" + fieldName + "' should not be null", fieldValue);

            assertEquals("Field '" + fieldName + "' should have value '" + expectedValue + "'",
                        expectedValue, fieldValue.asText());

            System.out.println("Field '" + fieldName + "': " + fieldValue + " ✓");

        } catch (Exception e) {
            fail("Error parsing JSON response: " + e.getMessage());
        }
    }

    // POST and PATCH request support
    @Given("I have invalid repository data:")
    public void i_have_invalid_repository_data(DataTable dataTable) {
        repositoryData = new HashMap<>();
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            repositoryData.put(entry.getKey(), entry.getValue());
        }
        System.out.println("Invalid repository data: " + repositoryData);
    }

    @Given("I have update data:")
    public void i_have_update_data(DataTable dataTable) {
        updateData = new HashMap<>();
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            Object value = entry.getValue();
            // Convert "false"/"true" strings to boolean for private field
            if ("private".equals(entry.getKey())) {
                value = Boolean.parseBoolean(entry.getValue());
            }
            updateData.put(entry.getKey(), value);
        }
        System.out.println("Update data: " + updateData);
    }

    @When("I send a POST request to {string} with the repository data")
    public void i_send_a_post_request_with_repository_data(String endpoint) {
        try {
            String fullUrl = baseUri + endpoint;
            System.out.println("Sending POST request to: " + fullUrl);

            String jsonBody = objectMapper.writeValueAsString(repositoryData);
            System.out.println("Request body: " + jsonBody);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Authorization", "Bearer " + authToken)
                .header("Accept", "application/vnd.github.v3+json")
                .header("Content-Type", "application/json")
                .header("User-Agent", "BDD-Cucumber-Testing")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status: " + response.statusCode());
            System.out.println("Response body: " + response.body());

        } catch (Exception e) {
            System.err.println("Error sending POST request: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to send POST request", e);
        }
    }

    @When("I send a PATCH request to {string} with the update data")
    public void i_send_a_patch_request_with_update_data(String endpoint) {
        try {
            // Replace {owner} placeholder with actual owner from repository name
            String actualEndpoint = endpoint.replace("{owner}", repositoryName.split("/")[0]);
            String fullUrl = baseUri + actualEndpoint;
            System.out.println("Sending PATCH request to: " + fullUrl);

            String jsonBody = objectMapper.writeValueAsString(updateData);
            System.out.println("Request body: " + jsonBody);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Authorization", "Bearer " + authToken)
                .header("Accept", "application/vnd.github.v3+json")
                .header("Content-Type", "application/json")
                .header("User-Agent", "BDD-Cucumber-Testing")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status: " + response.statusCode());
            System.out.println("Response body: " + response.body());

        } catch (Exception e) {
            System.err.println("Error sending PATCH request: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to send PATCH request", e);
        }
    }

    @And("the response should contain validation errors")
    public void the_response_should_contain_validation_errors() {
        // Verify that the response contains validation errors (422 status)
        assertNotNull("Response should not be null", response);
        assertNotNull("Response body should not be null", response.body());
        assertFalse("Response body should not be empty", response.body().isEmpty());

        try {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            assertTrue("Response should be a JSON object", jsonNode.isObject());

            // GitHub API typically returns validation errors in specific format
            assertTrue("Response should contain error information",
                      jsonNode.has("message") || jsonNode.has("errors"));

            System.out.println("Validation errors detected ✓");

        } catch (Exception e) {
            fail("Response body is not valid JSON: " + e.getMessage());
        }
    }
}
