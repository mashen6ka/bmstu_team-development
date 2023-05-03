#!/bin/bash

ENV_FILE=./application/sql/.env

echo PG_PASSWORD=$DB_PASSWORD >> $ENV_FILE
