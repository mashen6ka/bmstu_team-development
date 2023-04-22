#!/bin/bash
# get_data
sudo docker exec -i database sh -c "/sh/rollback_data.sh $1"
