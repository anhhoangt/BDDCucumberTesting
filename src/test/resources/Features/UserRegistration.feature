Feature: GitHub Issues REST API Testing

Background:
	Given I have a valid GitHub API token
	And I set the base URI to "https://api.github.com"
	And I have a test repository "BDDCucumberTesting"

Scenario: GET - Retrieve all issues from BDDCucumberTesting
	When I send a GET request to "/repos/{owner}/BDDCucumberTesting/issues" for issues
	Then the response status code should be 200
	And the response should be a JSON array

Scenario: GET - Retrieve all issues from BDDCucumber
	When I send a GET request to "/repos/{owner}/BDDCucumber/issues" for issues
	Then the response status code should be 200
	And the response should be a JSON array

Scenario: GET - Retrieve all issues from VersioningFileStorageSystem
	When I send a GET request to "/repos/{owner}/VersioningFileStorageSystem/issues" for issues
	Then the response status code should be 200
	And the response should be a JSON array

Scenario: GET - Retrieve issues with query parameters
	When I send a GET request to "/repos/{owner}/BDDCucumberTesting/issues" with parameters:
		| state | all |
		| sort | created |
		| direction | desc |
	Then the response status code should be 200

Scenario: POST - Create a new issue in BDDCucumberTesting
	Given I have issue data:
		| title | Test issue created via API |
		| body | This is a test issue created using GitHub REST API |
		| labels | ["bug", "api-test"] |
	When I send a POST request to "/repos/{owner}/BDDCucumberTesting/issues" with the issue data
	Then the response status code should be 201
	And the response should contain "title" field with value "Test issue created via API"
	And the response should contain "body" field
	And the response should contain "number" field
	And the created issue should exist

Scenario: POST - Create issue with invalid data
	Given I have invalid issue data:
		| title | |
		| body | Issue with empty title |
	When I send a POST request to "/repos/{owner}/BDDCucumberTesting/issues" with the issue data
	Then the response status code should be 422
	And the response should contain validation errors

Scenario Outline: Create issues with different labels in BDDCucumberTesting
	Given I have issue data with title "<title>" and labels "<labels>"
	When I send a POST request to "/repos/{owner}/BDDCucumberTesting/issues" with the issue data
	Then the response status code should be 201
	And the response should contain "title" field with value "<title>"
	And the response should contain the specified labels

	Examples:
		| title | labels |
		| Bug report issue | ["bug"] |
		| Feature request issue | ["enhancement"] |
		| Documentation issue | ["documentation"] |
		| Help wanted issue | ["help wanted"] |
