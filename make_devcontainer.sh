#!/bin/bash

if [ -z "$1" ]; then
    echo "no argument"
    exit 1
fi
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_rsa
docker compose build $1 --no-cache
