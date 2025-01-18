#!/bin/bash

# Variables
APP_URL="http://localhost:8080"  # Change this if your application runs on a different URL or port
MAX_RETRIES=10                  # Number of retries
SLEEP_DURATION=10               # Wait time between retries
LOG_FILE="/var/log/codedeploy_validate.log"

# Function to log messages
log_message() {
    echo "$(date '+%Y-%m-%d %H:%M:%S') - $1" | tee -a "$LOG_FILE"
}

log_message "Starting application health check for $APP_URL"

# Retry loop
for ((i=1; i<=MAX_RETRIES; i++)); do
    HTTP_CODE=$(curl -s -o /dev/null -w '%{http_code}' "$APP_URL")
    
    if [ "$HTTP_CODE" == "200" ]; then
        log_message "Health check passed! Application returned HTTP 200."
        exit 0
    else
        log_message "Attempt $i: Received HTTP Code $HTTP_CODE. Retrying in $SLEEP_DURATION seconds..."
        sleep $SLEEP_DURATION
    fi
done

log_message "Health check failed! Application did not return HTTP 200 after $MAX_RETRIES attempts."
exit 1
