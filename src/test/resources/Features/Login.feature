Feature: GitHub REST API Testing

Background:
	Given I have a valid GitHub API token
	And I set the base URI to "https://api.github.com"

@user-info
Scenario: GET - Retrieve authenticated user information
	When I send a GET request to "/user"
	Then the response status code should be 200
	And the response should contain user information
	And the response should have "login" field
	And the response should have "id" field.....



Scenario: GET - Retrieve BDDCucumberTesting repository
	Given I have a repository "anhhoangt/BDDCucumberTesting"
	When I send a GET request to "/repos/anhhoangt/BDDCucumberTesting"
	Then the response status code should be 200
	And the response should contain repository information
	And the response should have "name" field with value "BDDCucumberTesting"
	And the response should have "full_name" field
	And the response should have "owner" field

Scenario: GET - Retrieve VersioningFileStorageSystem repository
	Given I have a repository "anhhoangt/VersioningFileStorageSystem"
	When I send a GET request to "/repos/anhhoangt/VersioningFileStorageSystem"
	Then the response status code should be 200
	And the response should contain repository information
	And the response should have "name" field with value "VersioningFileStorageSystem"
	And the response should have "full_name" field
	And the response should have "owner" field

Scenario: POST - Create repository with invalid data
	Given I have invalid repository data:
		| name | |
		| description | Repository with empty name |
	When I send a POST request to "/user/repos" with the repository data
	Then the response status code should be 404
	And the response should contain validation errors

Scenario: PATCH - Update BDDCucumberTesting repository information
	Given I have a repository "anhhoangt/BDDCucumberTesting"
	And I have update data:
		| description | Updated via GitHub REST API testing framework |
		| private | false |
	When I send a PATCH request to "/repos/{owner}/BDDCucumberTesting" with the update data
	Then the response status code should be 404
	And the response should contain validation errors
