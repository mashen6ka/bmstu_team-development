\copy users from '/data/users.csv' with (format csv);
\copy places from '/data/places.csv' with (format csv);

ALTER SEQUENCE public.places_place_id_seq RESTART 31;
ALTER SEQUENCE public.users_user_id_seq RESTART 11;
