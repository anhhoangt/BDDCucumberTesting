Feature: GitHub Repository Management REST API Testing

Background:
	Given I have a valid GitHub API token
	And I set the base URI to "https://api.github.com"

Scenario Outline: Repository visibility and settings
	Given I have repository data:
		| name | <repo_name> |
		| description | <description> |
		| private | <is_private> |
		| has_issues | <has_issues> |
		| has_projects | <has_projects> |
		| has_wiki | <has_wiki> |
	When I send a POST request to "/user/repos" with the repository data
	Then the response status code should be 201
	And the response should contain "name" field with value "<repo_name>"
	And the response should contain "private" field with value <is_private>
	And the response should contain "has_issues" field with value <has_issues>

	Examples:
		| repo_name | description | is_private | has_issues | has_projects | has_wiki |
		| public-test-repo-temp | Public repository for testing | false | true | true | true |
		| private-test-repo-temp | Private repository for testing | true | false | false | false |

Scenario: GET - Retrieve BDDCucumberTesting collaborators
	Given I have a repository "anhhoangt/BDDCucumberTesting"
	When I send a GET request to "/repos/{owner}/BDDCucumberTesting/collaborators"
	Then the response status code should be 200
	And the response should be a JSON array

Scenario: GET - Retrieve BDDCucumber branches
	Given I have a repository "anhhoangt/BDDCucumber"
	When I send a GET request to "/repos/{owner}/BDDCucumber/branches"
	Then the response status code should be 200
	And the response should be a JSON array

Scenario: GET - Retrieve BDDCucumberTesting commits
	Given I have a repository "anhhoangt/BDDCucumberTesting"
	When I send a GET request to "/repos/{owner}/BDDCucumberTesting/commits"
	Then the response status code should be 200
	And the response should be a JSON array

Scenario: GET - Retrieve VersioningFileStorageSystem languages
	Given I have a repository "anhhoangt/VersioningFileStorageSystem"
	When I send a GET request to "/repos/{owner}/VersioningFileStorageSystem/languages"
	Then the response status code should be 200
	And the response should contain programming language information

Scenario: GET - Retrieve BDDCucumber topics
	Given I have a repository "anhhoangt/BDDCucumber"
	When I send a GET request to "/repos/{owner}/BDDCucumber/topics"
	Then the response status code should be 200
	And the response should contain "names" field

Scenario: PUT - Update BDDCucumberTesting topics
	Given I have a repository "anhhoangt/BDDCucumberTesting"
	And I have topic data:
		| names | ["api", "testing", "github", "rest", "cucumber", "bdd"] |
	When I send a PUT request to "/repos/{owner}/BDDCucumberTesting/topics" with the topic data
	Then the response status code should be 200
	And the response should contain "names" field

Scenario: GET - Retrieve BDDCucumber releases
	Given I have a repository "anhhoangt/BDDCucumber"
	When I send a GET request to "/repos/{owner}/BDDCucumber/releases"
	Then the response status code should be 200
	And the response should be a JSON array

Scenario: GET - Retrieve VersioningFileStorageSystem tags
	Given I have a repository "anhhoangt/VersioningFileStorageSystem"
	When I send a GET request to "/repos/{owner}/VersioningFileStorageSystem/tags" for repository operations
	Then the response status code should be 200
	And the response should be a JSON array

Scenario: GET - Retrieve BDDCucumber repository contents
	Given I have a repository "anhhoangt/BDDCucumber"
	When I send a GET request to "/repos/{owner}/BDDCucumber/contents" for repository operations
	Then the response status code should be 200
	And the response should be a JSON array
