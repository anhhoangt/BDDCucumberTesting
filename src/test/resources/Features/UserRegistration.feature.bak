Feature: GitHub Issues REST API Testing

Background:
	Given I have a valid GitHub API token
	And I set the base URI to "https://api.github.com"
	And I have a test repository "test-repo-api"

Scenario: GET - Retrieve all issues from a repository
	When I send a GET request to "/repos/{owner}/test-repo-api/issues" for issues
	Then the response status code should be 200
	And the response should be a JSON array
	And each issue should have "id" field
	And each issue should have "title" field
	And each issue should have "state" field

Scenario: GET - Retrieve issues with query parameters
	When I send a GET request to "/repos/{owner}/test-repo-api/issues" with parameters:
		| state | open |
		| labels | bug |
		| sort | created |
		| direction | desc |
	Then the response status code should be 200
	And all returned issues should have state "open"

Scenario: GET - Retrieve a specific issue
	Given I have an existing issue with number 1
	When I send a GET request to "/repos/{owner}/test-repo-api/issues/1" for issues
	Then the response status code should be 200
	And the response should contain issue information
	And the response should have "number" field with value 1
	And the response should have "title" field
	And the response should have "body" field

Scenario: POST - Create a new issue
	Given I have issue data:
		| title | Test issue created via API |
		| body | This is a test issue created using GitHub REST API |
		| labels | ["bug", "api-test"] |
	When I send a POST request to "/repos/{owner}/test-repo-api/issues" with the issue data
	Then the response status code should be 201
	And the response should contain "title" field with value "Test issue created via API"
	And the response should contain "body" field
	And the response should contain "number" field
	And the created issue should exist

Scenario: POST - Create issue with invalid data
	Given I have invalid issue data:
		| title | |
		| body | Issue with empty title |
	When I send a POST request to "/repos/{owner}/test-repo-api/issues" with the issue data
	Then the response status code should be 422
	And the response should contain validation errors

Scenario: PATCH - Update an existing issue
	Given I have an existing issue with number 1
	And I have update data:
		| title | Updated issue title |
		| body | Updated issue body via API |
		| state | closed |
		| labels | ["enhancement", "updated"] |
	When I send a PATCH request to "/repos/{owner}/test-repo-api/issues/1" with the update data for issues
	Then the response status code should be 200
	And the response should contain "title" field with value "Updated issue title"
	And the response should contain "state" field with value "closed"

Scenario: GET - Retrieve issue comments
	Given I have an existing issue with number 1
	When I send a GET request to "/repos/{owner}/test-repo-api/issues/1/comments" for issues
	Then the response status code should be 200
	And the response should be a JSON array
	And each comment should have "id" field
	And each comment should have "body" field
	And each comment should have "user" field

Scenario: POST - Add comment to an issue
	Given I have an existing issue with number 1
	And I have comment data:
		| body | This is a test comment added via API |
	When I send a POST request to "/repos/{owner}/test-repo-api/issues/1/comments" with the comment data
	Then the response status code should be 201
	And the response should contain "body" field with value "This is a test comment added via API"
	And the response should contain "id" field

Scenario Outline: Create issues with different labels
	Given I have issue data with title "<title>" and labels "<labels>"
	When I send a POST request to "/repos/{owner}/test-repo-api/issues" with the issue data
	Then the response status code should be 201
	And the response should contain "title" field with value "<title>"
	And the response should contain the specified labels

	Examples:
		| title | labels |
		| Bug report issue | ["bug"] |
		| Feature request issue | ["enhancement"] |
		| Documentation issue | ["documentation"] |
		| Help wanted issue | ["help wanted"] |
