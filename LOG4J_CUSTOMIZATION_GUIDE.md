# Log4j Logging Customization Guide

Complete guide to understanding and customizing logging in your BDD Cucumber testing framework.

---

## 📚 Table of Contents
1. [Why Logging?](#why-logging)
2. [Log Format Components](#log-format-components)
3. [Customization Examples](#customization-examples)
4. [How to Switch Configurations](#how-to-switch-configurations)
5. [Best Practices](#best-practices)

---

## 🤔 Why Logging?

### 1. **Debugging Failed Tests** 🐛
**Without logs:**
```
❌ Test Failed
```

**With logs:**
```
2025-12-10 01:26:08 INFO  Sending POST request to: https://api.github.com/repos/anhhoangt/BDDCucumberTesting/issues
2025-12-10 01:26:08 DEBUG Request body: {"title":"Bug report","body":"Test issue"}
2025-12-10 01:26:09 INFO  Response status: 404
2025-12-10 01:26:09 ERROR Expected status 201 but got 404
2025-12-10 01:26:09 ERROR Repository not found or insufficient permissions
```
**Now you know exactly what went wrong!**

### 2. **Track Test Execution Flow** 🔄
```
2025-12-10 01:26:07 INFO  Starting scenario: Create new issue
2025-12-10 01:26:07 DEBUG Setting base URI to: https://api.github.com
2025-12-10 01:26:08 DEBUG Preparing issue data: {title=Bug report}
2025-12-10 01:26:08 INFO  Sending POST request...
2025-12-10 01:26:09 INFO  Issue created successfully with number: 7
2025-12-10 01:26:09 INFO  Verifying issue exists...
2025-12-10 01:26:10 INFO  ✅ Scenario completed successfully
```

### 3. **Performance Analysis** ⏱️
```
2025-12-10 01:26:08 DEBUG API call started: GET /repos/anhhoangt/BDDCucumberTesting/issues
2025-12-10 01:26:11 DEBUG API call completed in 3.2 seconds
2025-12-10 01:26:11 WARN  Slow API response detected (>2s threshold)
```

### 4. **CI/CD Debugging** 🔄
When tests fail in Jenkins but pass locally:
```
Jenkins Log:
2025-12-10 01:26:09 ERROR Connection timeout after 30 seconds
2025-12-10 01:26:09 ERROR GitHub API not responding from Jenkins network
```

### 5. **Audit Trail** 📜
Keep permanent records:
```
2025-12-10 01:26:08 INFO  Test executed by: Jenkins Build #45
2025-12-10 01:26:08 INFO  Git commit: c8db96b9
2025-12-10 01:26:08 INFO  Environment: staging
2025-12-10 01:26:08 INFO  6 issues created during test run
```

---

## 🎨 Log Format Components

### Pattern Conversion Characters

| Pattern | Description | Example Output |
|---------|-------------|----------------|
| `%d{yyyy-MM-dd HH:mm:ss}` | Date and time | `2025-12-10 01:26:08` |
| `%d{HH:mm:ss.SSS}` | Time with milliseconds | `01:26:08.123` |
| `%-5p` | Log level (left-aligned, 5 chars) | `INFO ` or `DEBUG` |
| `%p` | Log level | `INFO` |
| `%c` | Full class name | `StepDefinitions.GitHubApiStepsSimple` |
| `%c{1}` | Short class name | `GitHubApiStepsSimple` |
| `%c{2}` | Package + class | `StepDefinitions.GitHubApiStepsSimple` |
| `%C` | Fully qualified class name | `StepDefinitions.GitHubApiStepsSimple` |
| `%M` | Method name | `i_send_a_post_request_with_issue_data` |
| `%L` | Line number | `98` |
| `%t` | Thread name | `main` or `TestRunner-1` |
| `%m` | Log message | `Response status: 200` |
| `%n` | New line | Line break |
| `%r` | Milliseconds since start | `12345` |
| `%x` | Nested diagnostic context | `` |

### Example Patterns & Output

#### **Pattern 1: Minimal**
```properties
ConversionPattern=%m%n
```
**Output:**
```
Response status: 200
Field 'title': "Bug report" ✓
```

#### **Pattern 2: Time + Level + Message**
```properties
ConversionPattern=%d{HH:mm:ss} %-5p %m%n
```
**Output:**
```
01:26:08 INFO  Response status: 200
01:26:09 DEBUG Field 'title': "Bug report" ✓
```

#### **Pattern 3: Full Details**
```properties
ConversionPattern=%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5p %c{2}.%M:%L - %m%n
```
**Output:**
```
2025-12-10 01:26:08.123 [main] INFO  StepDefinitions.GitHubApiStepsSimple.i_send_a_post_request_with_issue_data:723 - Response status: 200
```

#### **Pattern 4: Concise with Class**
```properties
ConversionPattern=%d{HH:mm:ss} %-5p [%c{1}] %m%n
```
**Output:**
```
01:26:08 INFO  [GitHubApiStepsSimple] Response status: 200
```

---

## 🎯 Customization Examples

### Example 1: **No Logs (Silent Mode)**
When you don't want any logs at all:

```properties
# Silent mode - only show errors
log4j.rootLogger=ERROR, console

log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.Target=System.out
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%m%n
```

### Example 2: **Minimal Console Output**
Perfect for quick test runs:

```properties
# Show only important messages
log4j.rootLogger=INFO, console

log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=[%p] %m%n

# Suppress framework noise
log4j.logger.io.restassured=OFF
log4j.logger.io.cucumber=WARN
```
**Output:**
```
[INFO] Response status: 200
[INFO] Issue created successfully
```

### Example 3: **Detailed Debug Mode**
For troubleshooting:

```properties
# Show everything
log4j.rootLogger=DEBUG, console, file

log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%d{HH:mm:ss.SSS} %-5p %c{1}:%L - %m%n

log4j.appender.file=org.apache.log4j.FileAppender
log4j.appender.file.File=logs/debug.log
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5p %c.%M:%L - %m%n

# Show all details
log4j.logger.StepDefinitions=TRACE
log4j.logger.io.cucumber=DEBUG
log4j.logger.io.restassured=DEBUG
```
**Output:**
```
01:26:08.123 DEBUG GitHubApiStepsSimple:98 - Sending POST request to: https://api.github.com/repos/anhhoangt/BDDCucumberTesting/issues
01:26:08.456 TRACE GitHubApiStepsSimple:723 - Request headers: {Authorization=Bearer ghp_..., Content-Type=application/json}
01:26:08.789 TRACE GitHubApiStepsSimple:724 - Request body: {"title":"Bug report","body":"Test issue","labels":["bug"]}
01:26:09.012 DEBUG GitHubApiStepsSimple:742 - Response status: 201
01:26:09.123 TRACE GitHubApiStepsSimple:743 - Response body: {"id":3713201494,"number":3,...}
```

### Example 4: **Time-Based File Rotation**
New log file every day:

```properties
log4j.rootLogger=INFO, file

log4j.appender.file=org.apache.log4j.DailyRollingFileAppender
log4j.appender.file.File=logs/test-execution.log
log4j.appender.file.DatePattern='.'yyyy-MM-dd
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %m%n
```
**Creates files:**
```
logs/test-execution.log.2025-12-08
logs/test-execution.log.2025-12-09
logs/test-execution.log.2025-12-10  (current)
```

### Example 5: **Multiple Appenders** (Different destinations)
Console for INFO, file for DEBUG:

```properties
log4j.rootLogger=DEBUG, console, debugFile

# Console: Only INFO and above
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.Threshold=INFO
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%d{HH:mm:ss} %-5p %m%n

# File: Everything (DEBUG and above)
log4j.appender.debugFile=org.apache.log4j.FileAppender
log4j.appender.debugFile.File=logs/debug.log
log4j.appender.debugFile.Threshold=DEBUG
log4j.appender.debugFile.layout=org.apache.log4j.PatternLayout
log4j.appender.debugFile.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss.SSS} %-5p %c:%L - %m%n
```

### Example 6: **Error-Only File**
Separate file for errors:

```properties
log4j.rootLogger=INFO, console, errors

log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%m%n

# Error file: Only ERROR and FATAL
log4j.appender.errors=org.apache.log4j.FileAppender
log4j.appender.errors.File=logs/errors-only.log
log4j.appender.errors.Threshold=ERROR
log4j.appender.errors.layout=org.apache.log4j.PatternLayout
log4j.appender.errors.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c:%L - %m%n
```

---

## 🔄 How to Switch Configurations

### Method 1: Replace log4j.properties (Recommended)
```bash
# Switch to debug mode
cp log4j-examples/log4j-debug.properties log4j.properties

# Switch to production mode
cp log4j-examples/log4j-production.properties log4j.properties

# Switch to simple mode
cp log4j-examples/log4j-simple.properties log4j.properties
```

### Method 2: Use Maven Property
```bash
mvn test -Dlog4j.configuration=file:log4j-examples/log4j-debug.properties
```

### Method 3: Jenkins Configuration
In Jenkinsfile, add environment variable:
```groovy
environment {
    LOG4J_CONFIG = 'log4j-examples/log4j-production.properties'
}

stage('Run Tests') {
    steps {
        sh 'mvn test -Dlog4j.configuration=file:${LOG4J_CONFIG}'
    }
}
```

### Method 4: Programmatic (In Code)
Add to TestRunner setup:
```java
static {
    System.setProperty("log4j.configuration", "log4j-debug.properties");
}
```

---

## 📋 Best Practices

### ✅ **Do's**

1. **Use INFO for Production**
   ```properties
   log4j.rootLogger=INFO, console, file
   ```

2. **Use DEBUG for Development**
   ```properties
   log4j.rootLogger=DEBUG, console
   ```

3. **Rotate Log Files**
   ```properties
   log4j.appender.file=org.apache.log4j.RollingFileAppender
   log4j.appender.file.MaxFileSize=10MB
   log4j.appender.file.MaxBackupIndex=5
   ```

4. **Include Timestamp**
   ```properties
   ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %m%n
   ```

5. **Separate Error Logs**
   ```properties
   log4j.appender.errors.Threshold=ERROR
   ```

### ❌ **Don'ts**

1. **Don't Use TRACE in Production**
   - Too verbose, slows down tests
   - Creates huge log files

2. **Don't Log Sensitive Data**
   ```java
   // BAD
   System.out.println("Token: " + authToken);

   // GOOD
   System.out.println("Token: " + authToken.substring(0, 10) + "...");
   ```

3. **Don't Use System.out.println()**
   ```java
   // BAD
   System.out.println("Response: " + response);

   // GOOD
   logger.info("Response received with status: " + statusCode);
   ```

4. **Don't Ignore Log File Size**
   - Always use RollingFileAppender or DailyRollingFileAppender

5. **Don't Log Inside Loops Excessively**
   ```java
   // BAD
   for (int i = 0; i < 1000; i++) {
       logger.debug("Processing item " + i);
   }

   // GOOD
   logger.info("Processing 1000 items...");
   // process items
   logger.info("Completed processing 1000 items");
   ```

---

## 🎯 Quick Configuration Selector

| Use Case | Log Level | Pattern | File Output |
|----------|-----------|---------|-------------|
| **Quick Test Run** | INFO | `[%p] %m%n` | No |
| **Local Development** | DEBUG | `%d{HH:mm:ss} %-5p %c{1}:%L - %m%n` | Yes |
| **CI/CD Pipeline** | INFO | `%d{yyyy-MM-dd HH:mm:ss} %-5p [%c{1}] %m%n` | Yes, Daily |
| **Debugging Failures** | TRACE | `%d{HH:mm:ss.SSS} %-5p %c.%M:%L - %m%n` | Yes |
| **Production Monitoring** | WARN | `%d{yyyy-MM-dd HH:mm:ss} %-5p %m%n` | Yes, Rolling |

---

## 📊 Log Level Hierarchy

```
OFF (No logging)
 ↑
FATAL (Critical errors)
 ↑
ERROR (Errors)
 ↑
WARN (Warnings)
 ↑
INFO (Important info) ← Recommended for production
 ↑
DEBUG (Detailed info) ← Recommended for development
 ↑
TRACE (Very detailed)
 ↑
ALL (Everything)
```

**When you set a level, you get that level + all above it.**

Example: If you set `DEBUG`, you get: TRACE, DEBUG, INFO, WARN, ERROR, FATAL

---

## 🚀 Quick Commands

### View Logs
```bash
# View real-time logs
tail -f logs/test-execution.log

# View last 100 lines
tail -n 100 logs/test-execution.log

# Search for errors
grep ERROR logs/test-execution.log

# Search for specific test
grep "Create new issue" logs/test-execution.log
```

### Clean Logs
```bash
# Delete old logs
rm logs/*.log

# Delete logs older than 7 days
find logs/ -name "*.log" -mtime +7 -delete
```

---

## 📝 Summary

**Answer to your questions:**

1. **Why logs?**
   - Debug failures
   - Track execution
   - Performance analysis
   - CI/CD insights
   - Audit trail

2. **Can you customize?**
   - **YES!** Completely customizable
   - Change format, level, destination
   - Multiple appenders
   - Different configs for different environments

**Current setup:** `/log4j.properties`
**Example configs:** `/log4j-examples/` folder

Start with the default, switch to debug when troubleshooting! 🎯
