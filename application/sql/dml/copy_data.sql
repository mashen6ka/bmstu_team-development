\set csvdir `echo $CSVDIR`

\set copy_users '\\copy users from ' :csvdir 'users.csv' ' with (format csv);'
\set copy_places '\\copy places from ' :csvdir 'places.csv' ' with (format csv);'

:copy_users
:copy_places

ALTER SEQUENCE public.places_place_id_seq RESTART 31;
ALTER SEQUENCE public.users_user_id_seq RESTART 11;
