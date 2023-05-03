create table public.users (
	user_id bigserial not null,
	login text not null,
	hash text not null,
	"name" text not null
);

alter table public.users 
add constraint pk_users primary key (user_id);

alter table public.users 
add constraint unique_users_users unique (login);

