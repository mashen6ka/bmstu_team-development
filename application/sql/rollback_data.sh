#!/bin/bash
# get_data
docker exec -i database sh -c "/sh/rollback_data.sh $1"
