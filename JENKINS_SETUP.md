# Jenkins CI/CD Setup Guide for BDD Cucumber Testing

This guide will walk you through setting up Jenkins for automated test execution of your BDD Cucumber API testing framework.

---

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Jenkins Installation](#jenkins-installation)
3. [Jenkins Configuration](#jenkins-configuration)
4. [Pipeline Setup](#pipeline-setup)
5. [Credentials Configuration](#credentials-configuration)
6. [Running the Pipeline](#running-the-pipeline)
7. [Viewing Test Reports](#viewing-test-reports)
8. [Troubleshooting](#troubleshooting)

---

## Prerequisites

### Required Software
- **Jenkins**: Version 2.400+ (LTS recommended)
- **Java JDK**: Version 11 or 17
- **Maven**: Version 3.6+
- **Git**: Latest version

### Required Jenkins Plugins
Install these plugins from Jenkins Plugin Manager (`Manage Jenkins` → `Manage Plugins`):

1. **Pipeline Plugin** (Core)
2. **Git Plugin**
3. **JUnit Plugin**
4. **HTML Publisher Plugin**
5. **Cucumber Reports Plugin** (Optional but recommended)
6. **Email Extension Plugin** (For notifications)
7. **Credentials Binding Plugin**

---

## Jenkins Installation

### Option 1: Install Jenkins on macOS

```bash
# Install Jenkins using Homebrew
brew install jenkins-lts

# Start Jenkins
brew services start jenkins-lts

# Jenkins will be available at: http://localhost:8080
```

### Option 2: Install Jenkins on Linux

```bash
# Add Jenkins repository
wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -
sudo sh -c 'echo deb https://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'

# Install Jenkins
sudo apt-get update
sudo apt-get install jenkins

# Start Jenkins
sudo systemctl start jenkins
sudo systemctl enable jenkins
```

### Option 3: Run Jenkins in Docker

```bash
# Pull Jenkins image
docker pull jenkins/jenkins:lts

# Run Jenkins container
docker run -d \
  -p 8080:8080 \
  -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  --name jenkins \
  jenkins/jenkins:lts

# Get initial admin password
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

### Initial Setup

1. Open browser and go to `http://localhost:8080`
2. Enter the initial admin password (found in console output or at `/var/jenkins_home/secrets/initialAdminPassword`)
3. Install suggested plugins
4. Create your first admin user
5. Configure Jenkins URL (keep default `http://localhost:8080` or use your domain)

---

## Jenkins Configuration

### 1. Configure JDK

**Navigate to:** `Manage Jenkins` → `Global Tool Configuration` → `JDK`

1. Click **Add JDK**
2. **Name**: `JDK-17` (must match the name in Jenkinsfile)
3. Uncheck "Install automatically" if using local JDK
4. **JAVA_HOME**: `/usr/local/opt/openjdk@17` (macOS) or `/usr/lib/jvm/java-17-openjdk-amd64` (Linux)
5. Click **Save**

**To find your JAVA_HOME:**
```bash
# macOS
/usr/libexec/java_home -V

# Linux
update-alternatives --config java
```

### 2. Configure Maven

**Navigate to:** `Manage Jenkins` → `Global Tool Configuration` → `Maven`

1. Click **Add Maven**
2. **Name**: `Maven-3.9` (must match the name in Jenkinsfile)
3. Check "Install automatically"
4. **Version**: 3.9.9 (or latest)
5. Click **Save**

**OR use local Maven:**
1. Uncheck "Install automatically"
2. **MAVEN_HOME**: `/usr/local/Cellar/maven/3.9.11/libexec` (find with `mvn -version`)

### 3. Configure Git

**Navigate to:** `Manage Jenkins` → `Global Tool Configuration` → `Git`

1. Git should be automatically detected
2. If not, click **Add Git** and provide path: `/usr/bin/git`

---

## Pipeline Setup

### Create a New Pipeline Job

1. **Navigate to:** Jenkins Dashboard → `New Item`
2. **Enter item name:** `BDD-Cucumber-API-Testing`
3. **Select:** `Pipeline`
4. Click **OK**

### Configure Pipeline

#### General Settings
- ✅ **Description**: `Automated BDD Cucumber API testing for GitHub REST API`
- ✅ **Discard old builds**: Keep last 10 builds

#### Build Triggers (Choose one or more)

**Option 1: Poll SCM (Check for changes periodically)**
```
H/15 * * * *
```
This checks for changes every 15 minutes.

**Option 2: GitHub Hook Trigger**
- ✅ Check "GitHub hook trigger for GITScm polling"
- Configure webhook in GitHub repository settings

**Option 3: Build Periodically (Scheduled runs)**
```
# Run every day at 2 AM
0 2 * * *

# Run every weekday at 9 AM
0 9 * * 1-5
```

#### Pipeline Definition

1. **Definition**: `Pipeline script from SCM`
2. **SCM**: `Git`
3. **Repository URL**: `https://github.com/anhhoangt/BDDCucumberTesting.git`
4. **Credentials**: (Add if private repo)
5. **Branch Specifier**: `*/main` or `*/master`
6. **Script Path**: `Jenkinsfile`

Click **Save**

---

## Credentials Configuration

### Add GitHub Token

1. **Navigate to:** `Manage Jenkins` → `Manage Credentials` → `(global)` → `Add Credentials`

2. **Configure:**
   - **Kind**: `Secret text`
   - **Scope**: `Global`
   - **Secret**: `ghp_your_github_token_here` (paste your GitHub token with `repo` scope)
   - **ID**: `github-api-token` (must match the credential ID in Jenkinsfile)
   - **Description**: `GitHub API Token for BDD Testing`

3. Click **Create**

### Verify Credential ID

The Jenkinsfile references: `credentials('github-api-token')`

Make sure your credential ID matches exactly: `github-api-token`

---

## Running the Pipeline

### Manual Build

1. Go to your pipeline job: `BDD-Cucumber-API-Testing`
2. Click **Build with Parameters**
3. Select parameters:
   - **TEST_SUITE**:
     - `all` - Run all 9 test scenarios
     - `issues` - Run only @issues tagged tests (2 scenarios)
     - `smoke` - Run smoke tests
   - **CLEAN_BUILD**:
     - ✅ `true` - Clean before building (recommended)
     - ❌ `false` - Skip clean step
4. Click **Build**

### View Build Progress

1. Click on the build number (e.g., `#1`)
2. Click **Console Output** to see real-time logs
3. Watch the pipeline stages execute:
   - ✅ Checkout
   - ✅ Environment Setup
   - ✅ Clean
   - ✅ Compile
   - ✅ Run Tests
   - ✅ Generate Reports
   - ✅ Quality Gate

---

## Viewing Test Reports

### JUnit Test Results

1. Go to your build (e.g., `#1`)
2. Click **Test Result**
3. View:
   - Total tests
   - Passed/Failed/Skipped
   - Test duration
   - Individual test details

### Cucumber HTML Report

1. Go to your build
2. Click **Cucumber HTML Report** (in left sidebar)
3. Interactive report shows:
   - Feature files
   - Scenario details
   - Step definitions
   - Screenshots (if configured)
   - Execution time

### Archived Artifacts

1. Go to your build
2. Click **Build Artifacts**
3. Download:
   - `Cucumber.json` - JSON report
   - Test results XML files

---

## Pipeline Stages Explained

### 1. Checkout
- Pulls latest code from Git repository
- Displays commit information
- Sets up Git environment variables

### 2. Environment Setup
- Displays Java version
- Displays Maven version
- Shows build metadata

### 3. Clean (Conditional)
- Runs `mvn clean` if `CLEAN_BUILD=true`
- Removes previous build artifacts

### 4. Compile
- Compiles source code: `mvn compile`
- Compiles test code: `mvn test-compile`

### 5. Run Tests
- Executes tests based on `TEST_SUITE` parameter
- Supports tag filtering (@issues, @smoke)
- Uses GitHub token from credentials

### 6. Generate Reports
- Archives JUnit XML reports
- Publishes HTML reports
- Archives Cucumber JSON reports
- Creates fingerprints for tracking

### 7. Quality Gate
- Calculates test pass percentage
- **Fails build if pass rate < 80%**
- Displays test metrics

---

## Environment Variables

The pipeline sets these environment variables:

| Variable | Description | Example |
|----------|-------------|---------|
| `GITHUB_TOKEN` | GitHub API token from Jenkins credentials | `ghp_xxx...` |
| `MAVEN_OPTS` | Maven JVM options | `-Xmx1024m -Xms512m` |
| `PROJECT_NAME` | Project display name | `BDD Cucumber API Testing` |
| `BUILD_USER` | User who triggered the build | `admin` or `Jenkins` |
| `GIT_COMMIT_HASH` | Short Git commit hash | `c8db96b` |
| `GIT_AUTHOR` | Commit author name | `Anh Hoang` |
| `GIT_COMMIT_MSG` | Commit message | `Add new test case` |

---

## Notifications (Optional)

### Email Notifications

Uncomment email sections in Jenkinsfile and configure:

1. **Navigate to:** `Manage Jenkins` → `Configure System` → `Extended E-mail Notification`

2. **Configure SMTP:**
   - **SMTP server**: `smtp.gmail.com`
   - **SMTP port**: `587`
   - **Use SSL/TLS**: ✅
   - **Credentials**: Add Gmail credentials

3. **Update Jenkinsfile** email addresses:
```groovy
to: 'your-email@example.com'
```

### Slack Notifications

Install Slack Notification Plugin and add:

```groovy
post {
    success {
        slackSend(
            color: 'good',
            message: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        )
    }
}
```

---

## Troubleshooting

### Issue 1: "GITHUB_TOKEN credential not found"

**Solution:**
1. Verify credential ID is exactly: `github-api-token`
2. Check credential scope is `Global`
3. Ensure Jenkins has permission to access credentials

### Issue 2: "Maven not found"

**Solution:**
1. Go to `Global Tool Configuration`
2. Check Maven name matches Jenkinsfile: `Maven-3.9`
3. Verify Maven is installed or enable automatic installation

### Issue 3: "JDK not found"

**Solution:**
1. Check JDK name matches Jenkinsfile: `JDK-17`
2. Verify JAVA_HOME path is correct
3. Run `java -version` in Jenkins node

### Issue 4: Tests fail with "401 Unauthorized"

**Solution:**
1. Verify GitHub token has `repo` + `user` scopes
2. Check token hasn't expired
3. Regenerate token if needed
4. Update Jenkins credential

### Issue 5: "HTML Publisher Plugin not working"

**Solution:**
1. Install "HTML Publisher Plugin"
2. Go to `Manage Jenkins` → `Script Console`
3. Run:
```groovy
System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "")
```

### Issue 6: Build stuck at "Pending"

**Solution:**
1. Check Jenkins executors: `Manage Jenkins` → `Manage Nodes and Clouds`
2. Increase number of executors if needed
3. Verify agent is online

---

## Advanced Configuration

### Parallel Test Execution

Modify Jenkinsfile to run tests in parallel:

```groovy
stage('Run Tests - Parallel') {
    parallel {
        stage('Smoke Tests') {
            steps {
                sh "mvn test -Dcucumber.filter.tags='@smoke'"
            }
        }
        stage('Issues Tests') {
            steps {
                sh "mvn test -Dcucumber.filter.tags='@issues'"
            }
        }
    }
}
```

### Multi-Branch Pipeline

For testing multiple branches:

1. Create **New Item** → **Multibranch Pipeline**
2. Configure branch sources
3. Jenkins auto-discovers branches with Jenkinsfile

### Docker Agent

Run tests in Docker container:

```groovy
agent {
    docker {
        image 'maven:3.9-openjdk-17'
        args '-v $HOME/.m2:/root/.m2'
    }
}
```

---

## Best Practices

1. ✅ **Use parameterized builds** for flexibility
2. ✅ **Enable build retention** to keep history
3. ✅ **Set up notifications** for build failures
4. ✅ **Use quality gates** to enforce standards
5. ✅ **Archive important artifacts**
6. ✅ **Keep Jenkinsfile in version control**
7. ✅ **Use credentials management** (never hardcode secrets)
8. ✅ **Tag builds** for releases
9. ✅ **Monitor build trends** over time
10. ✅ **Clean workspace** after builds (optional)

---

## Security Considerations

1. **Credentials**: Always use Jenkins Credentials, never commit tokens
2. **Script Security**: Review and approve any Groovy scripts
3. **Access Control**: Set up Role-Based Access Control (RBAC)
4. **Jenkins Updates**: Keep Jenkins and plugins updated
5. **Build Logs**: Be careful not to print sensitive information

---

## Summary

Your Jenkins pipeline is now configured to:
- ✅ Automatically checkout code from Git
- ✅ Compile and run tests with Maven
- ✅ Execute BDD Cucumber scenarios
- ✅ Generate comprehensive test reports
- ✅ Enforce quality gates (80% pass rate)
- ✅ Archive artifacts and test results
- ✅ Provide build notifications

**Next Steps:**
1. Push Jenkinsfile to your repository
2. Configure Jenkins job
3. Add GitHub token credential
4. Run your first build
5. View test reports

**Jenkins Job URL:** `http://localhost:8080/job/BDD-Cucumber-API-Testing/`

Happy Testing! 🚀
