#!/bin/bash

PROPERTIES=../application/server/src/main/resources/database.properties

echo db.url=$DB_URL > $PROPERTIES
echo db.username=$DB_USERNAME >> $PROPERTIES
echo db.password=$DB_PASSWORD >> $PROPERTIES
