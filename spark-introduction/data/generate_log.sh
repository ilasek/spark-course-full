#!/usr/bin/env bash
NUMBER_OF_MEASSAGES=1000
LOG_FILE="sample.log"
severities=("INFO" "WARNING" "ERROR" "DEBUG")

# Seed random generator
RANDOM=$$$(date +%s)

rm $LOG_FILE

for ((i = 0; i < NUMBER_OF_MEASSAGES; i++)); do
    severity=${severities[$RANDOM % ${#severities[@]}]}
    echo "$severity This is $severity message" >> $LOG_FILE
done