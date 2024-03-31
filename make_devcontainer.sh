#!/bin/bash

eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_rsa
docker compose build web_dev
