#!/bin/bash
# copy db data
docker cp . database:/

# generate tables
docker exec -i database sh -c "/sh/generate_db.sh $1"

