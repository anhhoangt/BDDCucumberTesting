Feature: Login Application feature

Scenario: Successful User Login
	Given User is on Application Home Page
	When Application Page Title is FREE CRM
	Then user enters valid username and password
	And user clicks on Login Button
	Then User should be redirected to Dashboard
	And User should see welcome message

Scenario: Login with Invalid Credentials
	Given User is on Application Home Page
	When Application Page Title is FREE CRM
	Then user enters invalid username and password
	And user clicks on Login Button
	Then User should see error message "Invalid credentials"
	And User should remain on login page

Scenario: Login with Empty Username
	Given User is on Application Home Page
	When Application Page Title is FREE CRM
	Then user enters empty username and valid password
	And user clicks on Login Button
	Then User should see error message "Username is required"

Scenario: Login with Empty Password
	Given User is on Application Home Page
	When Application Page Title is FREE CRM
	Then user enters valid username and empty password
	And user clicks on Login Button
	Then User should see error message "Password is required"

Scenario: Forgot Password Functionality
	Given User is on Application Home Page
	When User clicks on "Forgot Password" link
	Then User should be redirected to password reset page
	When User enters valid email address
	And User clicks on "Send Reset Link" button
	Then User should see confirmation message "Reset link sent to your email"

Scenario: Remember Me Functionality
	Given User is on Application Home Page
	When user enters valid username and password
	And User checks "Remember Me" checkbox
	And user clicks on Login Button
	Then User should be redirected to Dashboard
	When User closes browser and reopens application
	Then User should still be logged in

Scenario Outline: Login with Multiple Invalid Credentials
	Given User is on Application Home Page
	When user enters username "<username>" and password "<password>"
	And user clicks on Login Button
	Then User should see error message "<error_message>"

	Examples:
		| username | password | error_message |
		| invalid_user | valid_pass | Invalid username |
		| valid_user | invalid_pass | Invalid password |
		| | | Username and password are required |
		| admin | 123 | Password too short |
