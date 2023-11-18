#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <command> <filename>"
    exit 1
fi

command="$1"

filename="$2"

if [ -z "$filename" ]; then
    echo "Error: Please provide a filename"
    exit 1
fi

case "$command" in
    sync)
        java -jar ./jar/app-0.0.1-SNAPSHOT.jar sync $filename
        ;;
    upload)
        java -jar ./jar/app-0.0.1-SNAPSHOT.jar upload $filename
        ;;
    *)
        echo "Invalid command. Supported commands: sync, upload"
        exit 1
        ;;
esac