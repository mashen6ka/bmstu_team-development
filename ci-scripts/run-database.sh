#!/bin/bash
# copy db data
docker cp ./application/sql/ database:/

# generate tables
docker exec -i database sh -c "./application/sql/sh/generate_db.sh $1"

