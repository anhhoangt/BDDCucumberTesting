#!/bin/bash

# GitHub API Test Runner Script
# This script helps you run the BDD test with proper token setup

echo "GitHub API BDD Test Runner"
echo "=========================="

# Check if GITHUB_TOKEN environment variable is set
if [ -z "$GITHUB_TOKEN" ]; then
    echo "⚠️  GITHUB_TOKEN environment variable is not set"
    echo ""
    echo "To set up authentication:"
    echo "1. Go to https://github.com/settings/tokens"
    echo "2. Generate a new token with 'user' scope"
    echo "3. Export it: export GITHUB_TOKEN=your_token_here"
    echo "4. Run this script again"
    echo ""
    echo "Alternatively, you can run with:"
    echo "mvn test -Dgithub.token=your_token_here"
    exit 1
fi

echo "✅ GitHub token found"
echo "🚀 Running BDD tests..."
echo ""

# Run the Maven test
mvn test

echo ""
echo "Test execution completed!"
