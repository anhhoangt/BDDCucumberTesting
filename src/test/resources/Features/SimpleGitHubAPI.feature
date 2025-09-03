Feature: Simple GitHub API Testing

  Scenario: GET - Retrieve authenticated user information
    Given I have a valid GitHub API token
    And I set the base URI to "https://api.github.com"
    When I send a GET request to "/user"
    Then the response status code should be 200
    And the response should contain user information
