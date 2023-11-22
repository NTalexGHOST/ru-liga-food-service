create table if not exists service_user
(
    id uuid not null,
    password varchar(64) not null,
    role varchar(64) not null,
    account uuid not null,
    constraint user_pk primary key (id)
);

comment on table service_user is 'Сущность пользователя';
comment on column service_user.id is 'Идентификатор';
comment on column service_user.password is 'Пароль';
comment on column service_user.role is 'Роль пользователя';
comment on column service_user.account is 'Идентификатор связанной сущности';