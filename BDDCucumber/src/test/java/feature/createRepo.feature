Feature: Validate Create Repository & Delete Repository


Scenario Outline: Verify Create Repo API
		Given Starting Test Case "Verify Create Repo API"
    Given Create Repo Payload name "<name>" and description "<description>"
    When User calls Create API "/user/repos" Post API Call
    Then API Call got Successul with Status Code 201
    And Verify Repository "name" is "<name>"
    And Verify Repository "description" is "<description>"
    And Ending TestCase
    
Examples:
	| name 	| description |
	| Aiden | Data Driven Test Repo 1|
	| Kaylee | Data Driven Test Repo 1|
	| Matcha | Data Driven Test Repo 1|
    
Scenario Outline: Verify Delete Repo API
		Given Starting Test Case "Verify Delete Repo API"
    Given Create Repo Payload name "<name>" and description "<description>"
    When User calls Delete API "/repos/anhhoangt/" Delete Call
    Then API Call got Successul with Status Code 204
    And Ending TestCase
    
Examples:
	| name 	| description |
	| Aiden | Data Driven Test Repo 1|
	| Kaylee | Data Driven Test Repo 1|
	| Matcha | Data Driven Test Repo 1|