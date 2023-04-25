#!/bin/bash

ENV_FILE=./application/deploy/.env

echo PG_PASSWORD=$DB_PASSWORD >> $ENV_FILE
