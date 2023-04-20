#!/bin/bash
# This is a comment
sleep 1
PGPASSWORD=$1 psql journey -U dev -a -f /dml/copy_data.sql

