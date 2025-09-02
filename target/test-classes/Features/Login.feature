Feature: GitHub REST API Testing

Background:
	Given I have a valid GitHub API token
	And I set the base URI to "https://api.github.com"

Scenario: GET - Retrieve authenticated user information
	When I send a GET request to "/user"
	Then the response status code should be 200
	And the response should contain user information
	And the response should have "login" field
	And the response should have "id" field

Scenario: GET - Retrieve a specific repository
	Given I have a repository "anhhoangt/BDDCucumber"
	When I send a GET request to "/repos/anhhoangt/BDDCucumber"
	Then the response status code should be 200
	And the response should contain repository information
	And the response should have "name" field with value "BDDCucumber"
	And the response should have "owner" field

Scenario: GET - Retrieve non-existent repository
	When I send a GET request to "/repos/nonexistent/repository"
	Then the response status code should be 404
	And the response should contain error message

Scenario: POST - Create a new repository
	Given I have repository data:
		| name | test-repo-api |
		| description | Test repository created via API |
		| private | false |
	When I send a POST request to "/user/repos" with the repository data
	Then the response status code should be 201
	And the response should contain "name" field with value "test-repo-api"
	And the response should contain "description" field
	And the created repository should exist

Scenario: POST - Create repository with invalid data
	Given I have invalid repository data:
		| name | |
		| description | Repository with empty name |
	When I send a POST request to "/user/repos" with the repository data
	Then the response status code should be 422
	And the response should contain validation errors

Scenario: PATCH - Update repository information
	Given I have an existing repository "test-repo-api"
	And I have update data:
		| description | Updated description via API |
		| private | true |
	When I send a PATCH request to "/repos/{owner}/test-repo-api" with the update data
	Then the response status code should be 200
	And the response should contain "description" field with value "Updated description via API"
	And the response should contain "private" field with value "true"

Scenario: DELETE - Delete a repository
	Given I have an existing repository "test-repo-api"
	When I send a DELETE request to "/repos/{owner}/test-repo-api"
	Then the response status code should be 204
	And the repository should no longer exist
	When I send a GET request to "/repos/{owner}/test-repo-api"
	Then the response status code should be 404
