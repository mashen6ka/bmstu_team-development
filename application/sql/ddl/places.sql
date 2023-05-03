create table public.places (
	place_id bigserial not null,
	author_id bigint not null,
	is_visited boolean not null,
	title text not null,
	dttm_update bigint not null,
	card_text text,
	preview_image_path text,
	latitude numeric(16,8),
	longitude numeric(16,8),
	tag text
);

alter table public.places
add constraint pk_places primary key (place_id);

-- дата обновления больше чем 01.01.2023
alter table public.places
add constraint positive_date check (dttm_update > 1672531200);

alter table public.places
add constraint fk_author foreign key (author_id)
references public.users (user_id)
on delete cascade
on update cascade;

alter table public.places
add constraint check_latitude check (latitude between -180 and 180);

alter table public.places
add constraint check_longitude check (longitude between -180 and 180);

