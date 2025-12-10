pipeline {
    agent any

    tools {
        maven 'Maven-3.9' // Refers to Maven installation configured in Jenkins Global Tool Configuration
        jdk 'JDK-17'      // Refers to JDK installation configured in Jenkins Global Tool Configuration
    }

    environment {
        // GitHub token stored as Jenkins credential
        GITHUB_TOKEN = credentials('github-api-token')

        // Maven options
        MAVEN_OPTS = '-Xmx1024m -Xms512m'

        // Project metadata
        PROJECT_NAME = 'BDD Cucumber API Testing'
        BUILD_USER = "${currentBuild.getBuildCauses()[0].userId ?: 'Jenkins'}"
    }

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['all', 'issues', 'smoke'],
            description: 'Select which test suite to run'
        )
        booleanParam(
            name: 'CLEAN_BUILD',
            defaultValue: true,
            description: 'Clean build before testing'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "╔════════════════════════════════════════════╗"
                    echo "║  Checking out code from repository...     ║"
                    echo "╚════════════════════════════════════════════╝"
                }

                checkout scm

                script {
                    // Get Git commit information
                    env.GIT_COMMIT_MSG = sh(script: "git log -1 --pretty=%B", returnStdout: true).trim()
                    env.GIT_AUTHOR = sh(script: "git log -1 --pretty=%an", returnStdout: true).trim()
                    env.GIT_COMMIT_HASH = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()

                    echo "Git Commit: ${env.GIT_COMMIT_HASH}"
                    echo "Author: ${env.GIT_AUTHOR}"
                    echo "Message: ${env.GIT_COMMIT_MSG}"
                }
            }
        }

        stage('Environment Setup') {
            steps {
                script {
                    echo "╔════════════════════════════════════════════╗"
                    echo "║  Setting up build environment...          ║"
                    echo "╚════════════════════════════════════════════╝"
                }

                sh '''
                    echo "Java Version:"
                    java -version

                    echo "\nMaven Version:"
                    mvn -version

                    echo "\nBuild Information:"
                    echo "Build Number: ${BUILD_NUMBER}"
                    echo "Build ID: ${BUILD_ID}"
                    echo "Job Name: ${JOB_NAME}"
                    echo "Workspace: ${WORKSPACE}"
                '''
            }
        }

        stage('Clean') {
            when {
                expression { params.CLEAN_BUILD == true }
            }
            steps {
                script {
                    echo "╔════════════════════════════════════════════╗"
                    echo "║  Cleaning previous build artifacts...     ║"
                    echo "╚════════════════════════════════════════════╝"
                }

                sh 'mvn clean'
            }
        }

        stage('Compile') {
            steps {
                script {
                    echo "╔════════════════════════════════════════════╗"
                    echo "║  Compiling source code...                 ║"
                    echo "╚════════════════════════════════════════════╝"
                }

                sh 'mvn compile test-compile'
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    echo "╔════════════════════════════════════════════╗"
                    echo "║  Executing BDD Cucumber Tests...          ║"
                    echo "╚════════════════════════════════════════════╝"

                    // Determine which tests to run based on parameter
                    def testCommand = 'mvn test'

                    if (params.TEST_SUITE == 'issues') {
                        echo "Running @issues tagged tests only..."
                        testCommand = "mvn test -Dcucumber.filter.tags='@issues'"
                    } else if (params.TEST_SUITE == 'smoke') {
                        echo "Running smoke tests..."
                        testCommand = "mvn test -Dcucumber.filter.tags='@smoke'"
                    } else {
                        echo "Running all tests..."
                    }

                    // Execute tests
                    sh testCommand
                }
            }
        }

        stage('Generate Reports') {
            steps {
                script {
                    echo "╔════════════════════════════════════════════╗"
                    echo "║  Generating test reports...               ║"
                    echo "╚════════════════════════════════════════════╝"
                }

                // Archive test results
                junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'

                // Archive Cucumber JSON reports
                script {
                    if (fileExists('target/cucumber-reports/Cucumber.json')) {
                        archiveArtifacts artifacts: 'target/cucumber-reports/*.json', fingerprint: true
                        echo "Cucumber JSON reports archived successfully"
                    }
                }

                // Archive HTML reports
                script {
                    if (fileExists('test-output')) {
                        publishHTML([
                            allowMissing: false,
                            alwaysLinkToLastBuild: true,
                            keepAll: true,
                            reportDir: 'test-output',
                            reportFiles: 'index.html',
                            reportName: 'Cucumber HTML Report',
                            reportTitles: 'BDD Test Execution Report'
                        ])
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                script {
                    echo "╔════════════════════════════════════════════╗"
                    echo "║  Checking quality metrics...              ║"
                    echo "╚════════════════════════════════════════════╝"

                    // Read test results
                    def testResults = junit testResults: '**/target/surefire-reports/*.xml'

                    // Calculate pass percentage
                    def total = testResults.totalCount
                    def passed = testResults.passCount
                    def failed = testResults.failCount
                    def skipped = testResults.skipCount

                    if (total > 0) {
                        def passPercentage = (passed / total) * 100

                        echo "Test Results:"
                        echo "  Total: ${total}"
                        echo "  Passed: ${passed}"
                        echo "  Failed: ${failed}"
                        echo "  Skipped: ${skipped}"
                        echo "  Pass Rate: ${passPercentage.round(2)}%"

                        // Fail the build if pass rate is below 80%
                        if (passPercentage < 80) {
                            error("Quality gate failed: Pass rate (${passPercentage.round(2)}%) is below 80%")
                        } else {
                            echo "✅ Quality gate passed!"
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                echo "╔════════════════════════════════════════════╗"
                echo "║  Pipeline Execution Completed             ║"
                echo "╚════════════════════════════════════════════╝"

                // Clean workspace after build (optional)
                // cleanWs()
            }
        }

        success {
            script {
                echo "✅ BUILD SUCCESSFUL!"
                echo "All tests passed successfully."

                // Send notification (if configured)
                // emailext (
                //     subject: "✅ Jenkins Build Success: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                //     body: """
                //         Build succeeded for ${env.JOB_NAME}
                //
                //         Build Number: ${env.BUILD_NUMBER}
                //         Commit: ${env.GIT_COMMIT_HASH}
                //         Author: ${env.GIT_AUTHOR}
                //
                //         Check console output at: ${env.BUILD_URL}
                //     """,
                //     to: 'your-email@example.com'
                // )
            }
        }

        failure {
            script {
                echo "❌ BUILD FAILED!"
                echo "Please check the console output for details."

                // Send failure notification
                // emailext (
                //     subject: "❌ Jenkins Build Failed: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                //     body: """
                //         Build failed for ${env.JOB_NAME}
                //
                //         Build Number: ${env.BUILD_NUMBER}
                //         Commit: ${env.GIT_COMMIT_HASH}
                //         Author: ${env.GIT_AUTHOR}
                //
                //         Check console output at: ${env.BUILD_URL}
                //     """,
                //     to: 'your-email@example.com'
                // )
            }
        }

        unstable {
            script {
                echo "⚠️ BUILD UNSTABLE!"
                echo "Some tests failed but build completed."
            }
        }
    }
}
