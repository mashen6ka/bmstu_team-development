#!/bin/bash
# copy db data
sudo docker cp . database:/

# generate tables
sudo docker exec -i database sh -c "/sh/generate_db.sh $1"

