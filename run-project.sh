#!/bin/bash

# Check if the correct number of arguments is provided
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <command> <filename>"
    exit 1
fi

# Assign the first argument to the command variable
command="$1"

# Assign the second argument to the filename variable
filename="$2"

# Verify if the filename is provided
if [ -z "$filename" ]; then
    echo "Error: Please provide a filename"
    exit 1
fi

# Execute the Java application jar file with the specified command and filename
case "$command" in
    sync)
        java -jar ./target/app-0.0.1-SNAPSHOT.jar sync $filename
        ;;
    upload)
        java -jar ./target/app-0.0.1-SNAPSHOT.jar upload $filename
        ;;
    *)
        echo "Invalid command. Supported commands: sync, upload"
        exit 1
        ;;
esac