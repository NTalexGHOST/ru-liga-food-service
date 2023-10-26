create sequence if not exists courier_seq;

create table if not exists courier
(
    id bigint not null default nextval('courier_seq'),
    phone varchar(32) not null,
    status varchar(64) not null,
    coordinates point,
    constraint courier_pk primary key (id)
);

comment on table courier is 'Сущность курьера';
comment on column courier.id is 'Идентификатор';
comment on column courier.phone is 'Номер телефона';
comment on column courier.status is 'Статус';
comment on column courier.coordinates is 'Координаты';