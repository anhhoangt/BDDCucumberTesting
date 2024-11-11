Feature: Login Application feature

Scenario: User Login Scenario
	Given User is on Application Home Page
	When Application Page Title is FREE CRM
	Then user enters username and password
	And user clicks on Login Button
	When User navigate on user Profile page
	