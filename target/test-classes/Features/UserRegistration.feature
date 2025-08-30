Feature: User Registration feature

Scenario: Successful User Registration
	Given User is on Registration Page
	When User enters valid registration details
		| firstName | John |
		| lastName | Doe |
		| email | john.doe@example.com |
		| username | johndoe123 |
		| password | SecurePass123! |
		| confirmPassword | SecurePass123! |
	And User accepts terms and conditions
	And User clicks on Register Button
	Then User should be redirected to welcome page
	And User should see success message "Registration successful"
	And Confirmation email should be sent to user

Scenario: Registration with Existing Email
	Given User is on Registration Page
	When User enters registration details with existing email
		| firstName | Jane |
		| lastName | Smith |
		| email | existing@example.com |
		| username | janesmith |
		| password | Password123! |
		| confirmPassword | Password123! |
	And User clicks on Register Button
	Then User should see error message "Email already exists"
	And User should remain on registration page

Scenario: Registration with Weak Password
	Given User is on Registration Page
	When User enters registration details with weak password
		| firstName | Bob |
		| lastName | Wilson |
		| email | bob.wilson@example.com |
		| username | bobwilson |
		| password | 123 |
		| confirmPassword | 123 |
	And User clicks on Register Button
	Then User should see error message "Password must be at least 8 characters with special characters"

Scenario: Registration with Mismatched Passwords
	Given User is on Registration Page
	When User enters registration details with mismatched passwords
		| firstName | Alice |
		| lastName | Brown |
		| email | alice.brown@example.com |
		| username | alicebrown |
		| password | Password123! |
		| confirmPassword | DifferentPass123! |
	And User clicks on Register Button
	Then User should see error message "Passwords do not match"

Scenario Outline: Registration Field Validation
	Given User is on Registration Page
	When User enters "<firstName>", "<lastName>", "<email>", "<username>", "<password>"
	And User clicks on Register Button
	Then User should see error message "<error_message>"

	Examples:
		| firstName | lastName | email | username | password | error_message |
		| | Doe | john@example.com | johndoe | Pass123! | First name is required |
		| John | | john@example.com | johndoe | Pass123! | Last name is required |
		| John | Doe | invalid-email | johndoe | Pass123! | Please enter a valid email |
		| John | Doe | john@example.com | | Pass123! | Username is required |
		| John | Doe | john@example.com | ab | Pass123! | Username must be at least 3 characters |
