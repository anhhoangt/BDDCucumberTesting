# GitHub Token Setup Guide

## Quick Summary

**Current Status:** Your token has READ-ONLY permissions (`user` scope)
- ✅ Works: GET requests (reading data)
- ❌ Fails: POST/PATCH/PUT requests (creating/updating data)

**To run all 9 test scenarios**, you need a token with `repo` scope.

---

## Step-by-Step Instructions

### 1. Generate New Token

Visit: **https://github.com/settings/tokens**

Or navigate:
1. Click your profile picture (top right)
2. **Settings**
3. **Developer settings** (bottom left)
4. **Personal access tokens** → **Tokens (classic)**
5. **Generate new token** → **Generate new token (classic)**

### 2. Configure Token

**Token Name:** `BDD Cucumber Testing - Full Access`

**Expiration:** Choose based on your needs
- 90 days (recommended for testing)
- No expiration (if you're comfortable)

**Required Scopes:**

```
✅ repo
   ├─ Full control of private repositories
   ├─ Includes: repo:status, repo_deployment, public_repo
   └─ Allows: Create issues, update repos, manage topics

✅ user
   ├─ Read user profile data
   └─ Access user email
```

**Alternative (Public Repos Only):**
```
✅ public_repo (instead of full repo)
✅ user
```

### 3. Copy & Save Token

⚠️ **CRITICAL:** Copy the token immediately! You won't see it again.

The token looks like: `ghp_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx`

### 4. Update .env File

Replace the token in `/Users/anhthehoang/Desktop/learning-code/BDDCucumber/BDDCucumberTesting/.env`:

```bash
GITHUB_TOKEN=ghp_your_new_token_here
```

### 5. Test the New Token

#### Option A: Test with @issues scenarios (READ-ONLY)
```bash
cd /Users/anhthehoang/Desktop/learning-code/BDDCucumber/BDDCucumberTesting
export $(grep -v '^#' .env | grep -v '^$' | xargs) && mvn test
```

Expected: ✅ 2 tests pass

#### Option B: Test ALL 9 scenarios (requires repo scope)

1. Edit `TestRunner.java` and comment out the tags line:
```java
// tags = "@issues"  // Comment this out
```

2. Run tests:
```bash
export $(grep -v '^#' .env | grep -v '^$' | xargs) && mvn test
```

Expected with **repo scope**: ✅ All 9 tests pass
Expected with **user scope only**: ❌ 7 tests fail (POST/PATCH/PUT operations)

---

## Test Scenarios Breakdown

| # | Scenario | HTTP Method | Requires `repo` Scope? |
|---|----------|-------------|----------------------|
| 1 | GET issues from BDDCucumberTesting | GET | ❌ No (works with `user`) |
| 2 | GET issues from VersioningFileStorageSystem | GET | ❌ No (works with `user`) |
| 3 | GET issues with query parameters | GET | ❌ No (works with `user`) |
| 4 | POST - Create new issue | POST | ✅ **YES** |
| 5 | POST - Create invalid issue | POST | ✅ **YES** |
| 6 | Scenario Outline #1 - Bug label | POST | ✅ **YES** |
| 7 | Scenario Outline #2 - Enhancement label | POST | ✅ **YES** |
| 8 | Scenario Outline #3 - Documentation label | POST | ✅ **YES** |
| 9 | Scenario Outline #4 - Help wanted label | POST | ✅ **YES** |

---

## Verification Commands

### Check token scopes via GitHub API:
```bash
curl -H "Authorization: Bearer YOUR_TOKEN" https://api.github.com/user -I | grep "x-oauth-scopes"
```

### Quick permission test:
```bash
# Test if you can create an issue
curl -X POST \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Accept: application/vnd.github.v3+json" \
  https://api.github.com/repos/YOUR_USERNAME/BDDCucumberTesting/issues \
  -d '{"title":"Permission Test","body":"Testing token permissions"}'
```

If you see `201 Created`, your token has the correct permissions! ✅

---

## Troubleshooting

### Issue: Still getting 404 errors after updating token

**Solutions:**
1. Verify the token was copied correctly (no extra spaces)
2. Check token hasn't expired
3. Confirm `repo` scope is selected (not just `public_repo`)
4. Make sure the repository exists and you have access to it

### Issue: Token works in browser but not in tests

**Solutions:**
1. Reload environment variables:
   ```bash
   export $(grep -v '^#' .env | grep -v '^$' | xargs)
   ```
2. Restart terminal session
3. Clear Maven cache: `mvn clean`

### Issue: "Resource not accessible by personal access token"

**Solution:** Your token needs `repo` scope, not just `public_repo`

---

## Security Best Practices

1. ✅ Never commit `.env` file (it's in `.gitignore`)
2. ✅ Use tokens with minimum required scopes
3. ✅ Set expiration dates on tokens
4. ✅ Rotate tokens regularly (every 90 days)
5. ✅ Delete unused tokens from GitHub settings
6. ⚠️ Never share tokens in screenshots or pastes

---

## Summary

**Current Setup:** READ-ONLY (2 scenarios pass)
**Upgrade To:** FULL ACCESS with `repo` scope (9 scenarios pass)

**Next Step:** Generate new token with `repo` + `user` scopes → Update `.env` → Run tests
