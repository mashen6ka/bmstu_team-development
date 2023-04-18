create table public.subscriptions (
	user1_id bigint not null,
	user2_id bigint not null,
	is_mutual boolean not null
);

alter table public.subscriptions
add constraint pk_subscriptions primary key (user1_id, user2_id);

alter table public.subscriptions
add constraint fk_user1_id foreign key (user1_id)
references public.users (user_id)
on delete cascade
on update cascade;

alter table public.subscriptions
add constraint fk_user2_id foreign key (user2_id)
references public.users (user_id)
on delete cascade
on update cascade;

