Feature: User Profile Management feature

Background:
	Given User is logged into the application
	And User is on Dashboard page

Scenario: View User Profile
	When User clicks on Profile menu
	Then User should be redirected to profile page
	And User should see their profile information
		| Field | Value |
		| Name | John Doe |
		| Email | john.doe@example.com |
		| Username | johndoe123 |
		| Phone | +1-555-123-4567 |

Scenario: Update Profile Information
	Given User is on profile page
	When User updates profile information
		| firstName | Jane |
		| lastName | Smith |
		| phone | +1-555-987-6543 |
		| address | 123 Main St, City, State |
	And User clicks on Save Changes button
	Then User should see success message "Profile updated successfully"
	And Updated information should be displayed

Scenario: Change Password
	Given User is on profile page
	When User clicks on Change Password tab
	And User enters current password "OldPassword123!"
	And User enters new password "NewPassword456!"
	And User confirms new password "NewPassword456!"
	And User clicks on Update Password button
	Then User should see success message "Password changed successfully"
	And User should be able to login with new password

Scenario: Change Password with Wrong Current Password
	Given User is on profile page
	When User clicks on Change Password tab
	And User enters current password "WrongPassword"
	And User enters new password "NewPassword456!"
	And User confirms new password "NewPassword456!"
	And User clicks on Update Password button
	Then User should see error message "Current password is incorrect"

Scenario: Upload Profile Picture
	Given User is on profile page
	When User clicks on profile picture
	And User selects image file "profile.jpg"
	And User clicks on Upload button
	Then User should see success message "Profile picture updated"
	And New profile picture should be displayed

Scenario: Delete Account
	Given User is on profile page
	When User clicks on Account Settings tab
	And User clicks on Delete Account button
	And User confirms account deletion
	And User enters password for confirmation
	Then User should see confirmation message "Account will be deleted in 30 days"
	And User should be logged out
	And User should receive account deletion email

Scenario: Update Email Address
	Given User is on profile page
	When User updates email address to "newemail@example.com"
	And User clicks on Save Changes button
	Then User should see message "Verification email sent to new address"
	And User should verify new email address
	When User clicks verification link in email
	Then Email address should be updated
	And User should see success message "Email address updated successfully"

Scenario: Privacy Settings
	Given User is on profile page
	When User clicks on Privacy Settings tab
	And User updates privacy settings
		| Setting | Value |
		| Profile Visibility | Private |
		| Email Visibility | Friends Only |
		| Phone Visibility | Hidden |
		| Activity Status | Offline |
	And User clicks on Save Settings button
	Then User should see success message "Privacy settings updated"
	And Settings should be applied to user profile
