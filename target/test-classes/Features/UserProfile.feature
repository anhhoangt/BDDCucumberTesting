Feature: GitHub Repository Management REST API Testing

Background:
	Given I have a valid GitHub API token
	And I set the base URI to "https://api.github.com"
	And I have a test repository "test-repo-api"

Scenario: GET - Retrieve repository branches
	When I send a GET request to "/repos/{owner}/test-repo-api/branches"
	Then the response status code should be 200
	And the response should be a JSON array
	And each branch should have "name" field
	And each branch should have "commit" field
	And the response should contain a branch named "main"

Scenario: GET - Retrieve a specific branch
	When I send a GET request to "/repos/{owner}/test-repo-api/branches/main"
	Then the response status code should be 200
	And the response should contain "name" field with value "main"
	And the response should contain "commit" field
	And the response should contain "protected" field

Scenario: GET - Retrieve repository collaborators
	When I send a GET request to "/repos/{owner}/test-repo-api/collaborators"
	Then the response status code should be 200
	And the response should be a JSON array
	And each collaborator should have "login" field
	And each collaborator should have "permissions" field

Scenario: GET - Retrieve repository pull requests
	When I send a GET request to "/repos/{owner}/test-repo-api/pulls"
	Then the response status code should be 200
	And the response should be a JSON array
	And each pull request should have "number" field
	And each pull request should have "title" field
	And each pull request should have "state" field

Scenario: GET - Retrieve pull requests with filters
	When I send a GET request to "/repos/{owner}/test-repo-api/pulls" with parameters:
		| state | open |
		| sort | created |
		| direction | desc |
		| base | main |
	Then the response status code should be 200
	And all returned pull requests should have state "open"

Scenario: POST - Create a new pull request
	Given I have a feature branch "feature-branch"
	And I have pull request data:
		| title | Test PR created via API |
		| body | This is a test pull request created using GitHub REST API |
		| head | feature-branch |
		| base | main |
	When I send a POST request to "/repos/{owner}/test-repo-api/pulls" with the pull request data
	Then the response status code should be 201
	And the response should contain "title" field with value "Test PR created via API"
	And the response should contain "number" field
	And the response should contain "state" field with value "open"

Scenario: PATCH - Update a pull request
	Given I have an existing pull request with number 1
	And I have update data:
		| title | Updated PR title |
		| body | Updated PR body via API |
		| state | closed |
	When I send a PATCH request to "/repos/{owner}/test-repo-api/pulls/1" with the update data
	Then the response status code should be 200
	And the response should contain "title" field with value "Updated PR title"
	And the response should contain "state" field with value "closed"

Scenario: GET - Retrieve repository releases
	When I send a GET request to "/repos/{owner}/test-repo-api/releases"
	Then the response status code should be 200
	And the response should be a JSON array
	And each release should have "tag_name" field
	And each release should have "name" field

Scenario: POST - Create a new release
	Given I have release data:
		| tag_name | v1.0.0 |
		| name | Version 1.0.0 |
		| body | First release created via API |
		| draft | false |
		| prerelease | false |
	When I send a POST request to "/repos/{owner}/test-repo-api/releases" with the release data
	Then the response status code should be 201
	And the response should contain "tag_name" field with value "v1.0.0"
	And the response should contain "name" field with value "Version 1.0.0"

Scenario: DELETE - Delete a release
	Given I have an existing release with id 12345
	When I send a DELETE request to "/repos/{owner}/test-repo-api/releases/12345"
	Then the response status code should be 204
	And the release should no longer exist

Scenario: GET - Retrieve repository contents
	When I send a GET request to "/repos/{owner}/test-repo-api/contents"
	Then the response status code should be 200
	And the response should be a JSON array
	And each content item should have "name" field
	And each content item should have "type" field
	And the response should contain a file named "README.md"

Scenario Outline: Repository visibility and settings
	Given I have repository settings data:
		| private | <private> |
		| has_issues | <has_issues> |
		| has_wiki | <has_wiki> |
		| has_downloads | <has_downloads> |
	When I send a PATCH request to "/repos/{owner}/test-repo-api" with the settings data
	Then the response status code should be 200
	And the response should contain "private" field with value "<private>"
	And the response should contain "has_issues" field with value "<has_issues>"

	Examples:
		| private | has_issues | has_wiki | has_downloads |
		| true | true | true | true |
		| false | false | false | false |
