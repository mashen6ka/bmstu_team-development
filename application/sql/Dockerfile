FROM postgres:14.5

ENV CSVDIR /data/lib/postgresql/csvs/

COPY ./data/ ${CSVDIR}

COPY ./ddl/users.sql /docker-entrypoint-initdb.d/01_users.sql
COPY ./ddl/places.sql /docker-entrypoint-initdb.d/02_places.sql
COPY ./dml/copy_data.sql /docker-entrypoint-initdb.d/03_copy_data.sql